package zzuli.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.Snippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.service.PTAService;
import zzuli.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: PTAServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/13
 */
@Service
@Slf4j
public class PTAServiceImpl implements PTAService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取题目列表
     * @param contestId
     * @param Jsession
     * @param PTASession
     * @return
     */
    @Override
    public String getProblemList(String contestId, String Jsession, String PTASession) {
        String url = "https://pintia.cn/api/problem-sets/"+contestId+"/preview/problems";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("problem_type", "PROGRAMMING");
        paramMap.put("page", "0");
        paramMap.put("limit","100");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "JSESSIONID="+UUID.randomUUID().toString().replace("-", "").toUpperCase()+";"+"PTASession="+PTASession);
        headerMap.put("Accept-Encoding","gzip, deflate, br, zstd");
        headerMap.put("Accept","application/json;charset=UTF-8");
        String result = HttpClientUtil.doGet(url, paramMap, headerMap);
        if(result==null){
            log.error("访问PTA失败，不能成功获取题目列表");
        }
        return result;
    }


    /**
     * 获取考生学号与ptaId的对应关系并存到redis中
     * @param contestId
     */
    @Override
    public String UpMemberId(String contestId, String Jsession, String PTASession,String page) {
        String url = "https://pintia.cn/api/problem-sets/"+contestId+"/exam-members";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("limit","200");
        paramMap.put("page",page);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "JSESSIONID="+ UUID.randomUUID().toString().replace("-", "").toUpperCase()+";"+"PTASession="+PTASession);
        headerMap.put("Accept-Encoding","gzip, deflate, br, zstd");
        headerMap.put("Accept","application/json;charset=UTF-8");

        String result = HttpClientUtil.doGet(url, paramMap, headerMap);
        if(result == null || result.isEmpty()){
            log.error("访问PTA失败，不能成功获取考生学号与ptaId的对应关系");
        }
        return  result;
    }

    /**
     * 获取评测记录
     * @param contestId
     * @param Jsession
     * @param ptaSession
     * @param before
     * @return
     */
    @Override
    public String getRecord(String contestId, String Jsession, String ptaSession, String before) {
        String url = "https://pintia.cn/api/problem-sets/"+contestId+"/submissions";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("limit","200");
        paramMap.put("before",before);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "JSESSIONID="+UUID.randomUUID().toString().replace("-", "").toUpperCase()+";"+"PTASession="+ptaSession);
        headerMap.put("Accept-Encoding","gzip, deflate, br, zstd");
        headerMap.put("Accept","application/json;charset=UTF-8");


        String result = HttpClientUtil.doGet(url, paramMap, headerMap);
        if(result == null || result.isEmpty() ){
            log.error("访问PTA失败，不能成功更新评测记录");
        }
        return  result;
    }

    @Override
    public String UpRecordById(String id, String jsession, String ptaSession) {
        String url = "https://pintia.cn/api/submissions/"+id;
        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "JSESSIONID="+UUID.randomUUID().toString().replace("-", "").toUpperCase()+"; "+"PTASession="+ptaSession);
        headerMap.put("Accept-Encoding","gzip, deflate, br, zstd");
        headerMap.put("Accept","application/json;charset=UTF-8");
        String result = HttpClientUtil.doGet(url, paramMap, headerMap);
        if(result == null || result.isEmpty()){
            log.error("访问PTA失败，不能成功更新评测记录(原始记录为测评中)");
        }
        return  result;
    }
}
