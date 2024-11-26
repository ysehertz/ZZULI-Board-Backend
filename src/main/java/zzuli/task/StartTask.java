package zzuli.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zzuli.mapper.ContestMapper;
import zzuli.pojo.entity.Contest;

import java.sql.Timestamp;
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
    private  GetRecordTask getRecordTask;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    // 检查是否有比赛将要开始，为要开始的比赛创建任务
//    @Scheduled(cron = "0/${task.frequency.start} * * * * ? ")
    public void checkStart() {
        //获取key为notBegin的map类型的所有数据
        Map<String, String> notBeginMap = redisTemplate.opsForHash().entries("notBegin");
        if(notBeginMap == null || notBeginMap.size() == 0) {
            List<Contest> contestList  =  contestMapper.getAllContest();
            contestList.forEach(contest -> {
                // 如果该比赛还未结束
                if(contest.getEndTime().getTime() > System.currentTimeMillis()) {
                    //将比赛id和比赛开始时间存入到key为notBegin的map中
                    redisTemplate.opsForHash().put("notBegin", contest.getContestId(), contest.getStartTime().toString()+"-"+contest.getEndTime().toString());
                }
            });
            return;
        }
        //遍历map
        notBeginMap.forEach((k, v) -> {
            // 分别获取字符串v中“-”前后的数据
            String[] parts = v.split("-");
            String startTime = parts[0];
            String endTime = parts[1];

            //如果比赛开始时间小于当前时间加上5分钟
            if (Long.parseLong(startTime) <= System.currentTimeMillis() + 300000) {
                //为要开始的比赛创建定时任务
                getRecordTask.getRecord(k,new Timestamp(Long.parseLong(endTime)));
                //将比赛从notBegin中删除
                redisTemplate.opsForHash().delete("notBegin", k);
            }
        });
    }
}
