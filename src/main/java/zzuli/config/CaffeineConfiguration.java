package zzuli.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfiguration {
    @Bean
    @Primary
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats();
    }

    @Bean
    @Primary
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean("recordCacheManager")
    public CacheManager recordCacheManager() {
        Caffeine<Object, Object> recordCaffeine = Caffeine.newBuilder()
                .initialCapacity(50)
                .maximumSize(20000)
                .expireAfterWrite(30, TimeUnit.SECONDS) // 30秒过期
                .recordStats();
        
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(recordCaffeine);
        cacheManager.setCacheNames(java.util.Arrays.asList("contestRecord")); // 指定缓存名称
        return cacheManager;
    }
} 