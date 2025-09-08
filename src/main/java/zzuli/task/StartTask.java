package zzuli.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zzuli.common.Context.Judging;
import zzuli.mapper.ContestMapper;
import zzuli.mapper.RecordMapper;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Record;
import zzuli.service.PTAService;
import zzuli.utils.CacheService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: StartTsak
 * Package: zzuli.task
 * Description: 检查是否有比赛将要开始，为要开始的比赛创建任务
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/14
 */
@Component
@Slf4j
@EnableScheduling
public class StartTask {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PTAService ptaService;
    @Autowired
    private  GetRecordTask getRecordTask;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    @Qualifier("recordCacheManager")
    private CacheManager recordCacheManager;

    // 检查是否有比赛将要开始，为要开始的比赛创建任务
    @Scheduled(cron = "0/${task.frequency.start} * * * * ? ")
    public void checkStart() {
        //获取key为notBegin的map类型的所有数据
        Map<Object, Object> notBeginMapObj = cacheService.entries("notBegin");
        Map<String, String> notBeginMap = new HashMap<>();
        if (notBeginMapObj != null) {
            for (Map.Entry<Object, Object> entry : notBeginMapObj.entrySet()) {
                notBeginMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        if(notBeginMap.size() == 0) {
            cacheService.put("notBegin", "PreventNotBeginReclamation", "1956175552000=956175552010");
            List<Contest> contestList  =  contestMapper.getAllContest();
            contestList.forEach(contest -> {
                // 如果该比赛还未结束
                if(contest.getEndTime().getTime() > System.currentTimeMillis()) {
                    notBeginMap.put(contest.getContestId(), contest.getStartTime().getTime()+"="+contest.getEndTime().getTime());
                    //将比赛id和比赛开始时间存入到key为notBegin的map中
                    cacheService.put("notBegin", contest.getContestId(), contest.getStartTime().getTime()+"="+contest.getEndTime().getTime());
                }
            });
        }
        //遍历map
        notBeginMap.forEach((k, v) -> {
            // 分别获取字符串v中"-"前后的数据
            String[] parts = v.split("=");
            String startTime = parts[0];
            String endTime = parts[1];

            //如果比赛开始时间小于当前时间加上5分钟
            if (Long.parseLong(startTime) <= System.currentTimeMillis() + 300000) {
                //为要开始的比赛创建定时任务
                getRecordTask.getRecord(k,new Timestamp(Long.parseLong(endTime)));
                //将比赛从notBegin中删除
                cacheService.evict("notBegin", k);
            }
        });
    }

    // 检查是否有判题中的提交记录
    @Scheduled(cron = "0/10 * * * * ? ")
    public void checkJudging() {
        log.info("检查是否有判题中的提交记录", Judging.JUDGING);
        if(Judging.JUDGING.size() != 0){
            for(String id : Judging.JUDGING){
                String contestId = recordMapper.getContest(id);
                Contest contest = contestMapper.getContestById(contestId);
                String result = ptaService.UpRecordById(id,contest.getJsession(), contest.getPTASession());
                JsonNode rootNode = null;
                try {
                    rootNode = objectMapper.readTree(result);
                    JsonNode addressNode = rootNode.path("submission");
                    zzuli.pojo.entity.Record record = objectMapper.readValue(addressNode.toString(), Record.class);
                    //获取redis中S+考场ID的map类型中key为record.getUserId()的value
                    String studentNumber = (String) cacheService.get("S"+contestId, record.getUserId());
                    record.setMemberId(studentNumber);
                    record.setContestId(contestId);
                    if(contest.getContestType().equals("gplt")) {
                        Judging.JUDGING.remove(id);
                        Integer oldScore = (Integer) cacheService.get("C"+contestId+":"+studentNumber+":"+record.getProblemSetProblemId(), "score");
                        if(oldScore == null){
                            upRecord(record);
                        }else if(record.getScore() >= oldScore ){
                            upRecord(record);
                        }else {
                            recordMapper.UpRecord(record);
                        }
                    }else {
                        upRecord(record);
                    }
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
    /**
     * 更新测评记录
     * @param record
     */
    private void upRecord(Record record){
        recordMapper.UpRecord(record);
        // 将数据存到redis当中
        Map<String, Object> KVMap = new HashMap<>();
        KVMap.put("record_id", record.getId());
        KVMap.put("member_id", record.getMemberId());
        KVMap.put("problem_id", record.getProblemSetProblemId());
        KVMap.put("status", record.getStatus());
        KVMap.put("score", record.getScore());
        KVMap.put("language", record.getCompiler());
        KVMap.put("submit_time", record.getSubmitAt());
        KVMap.put("balloon", false);
        cacheService.putAll("C"+record.getContestId()+":"+record.getMemberId()+":"+record.getProblemSetProblemId(), KVMap);
        
        // 清除对应比赛的记录缓存
        recordCacheManager.getCache("contestRecord").evict(record.getContestId());
    }

}
