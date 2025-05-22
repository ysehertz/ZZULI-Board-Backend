package zzuli.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 统一缓存服务：本地Caffeine为一级缓存，Redis为二级缓存
 */
@Component
public class CacheService {
    private Cache<String, Object> localCache;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        localCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
    }

    public Object get(String key) {
        Object value = localCache.getIfPresent(key);
        if (value != null) {
            return value;
        }
        value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            localCache.put(key, value);
        }
        return value;
    }

    public void put(String key, Object value) {
        localCache.put(key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void evict(String key) {
        localCache.invalidate(key);
        redisTemplate.delete(key);
    }

    public void clearLocal() {
        localCache.invalidateAll();
    }

    // Hash结构：put(key, hashKey, value)
    public void put(String key, String hashKey, Object value) {
        // 本地缓存可用key:hashKey拼接
        localCache.put(key + ":" + hashKey, value);
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // Hash结构：get(key, hashKey)
    public Object get(String key, String hashKey) {
        Object value = localCache.getIfPresent(key + ":" + hashKey);
        if (value != null) {
            return value;
        }
        value = redisTemplate.opsForHash().get(key, hashKey);
        if (value != null) {
            localCache.put(key + ":" + hashKey, value);
        }
        return value;
    }

    // Hash结构：evict(key, hashKey)
    public void evict(String key, String hashKey) {
        localCache.invalidate(key + ":" + hashKey);
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    // Hash结构：putAll(key, map)
    public void putAll(String key, java.util.Map<String, Object> map) {
        for (String hashKey : map.keySet()) {
            localCache.put(key + ":" + hashKey, map.get(hashKey));
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    // Hash结构：entries(key)
    public java.util.Map<Object, Object> entries(String key) {
        // 只从Redis取，后续如需本地缓存可扩展
        return redisTemplate.opsForHash().entries(key);
    }

    // 根据key模式获取所有匹配的key集合
    public java.util.Set<String> entriesKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }
} 