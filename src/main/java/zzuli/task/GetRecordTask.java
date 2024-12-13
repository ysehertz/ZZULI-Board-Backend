package zzuli.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zzuli.pojo.pta.PTASession;
import zzuli.service.ContestService;

import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * ClassName: GetRecord
 * Package: zzuli.task
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/14
 */
@Component
@Slf4j
public class GetRecordTask {
    @Autowired
    private ContestService contestService;
    @Value("${task.frequency.getRecord}")
    private int period;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void getRecord(String contestId ,java.sql.Timestamp endTime) {
       PTASession ptaSession = contestService.getPTASession(contestId);
       getRecord_(contestId, endTime, ptaSession.getJsession(), ptaSession.getPTASession());
    }

    public void getRecord_(String contestId,java.sql.Timestamp endTime,String Jsession, String ptaService) {
        Runnable task = () -> {
            log.info("获取比赛记录");
            // 如果现在的时间小于比赛结束时间再加五分钟
            if (System.currentTimeMillis() < endTime.getTime() + 3600) {
                getRecord__(contestId, Jsession, ptaService);
            } else {
                log.info("比赛结束，停止获取比赛记录,比赛id:{}", contestId);
                shutdownScheduler();
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
    }
    // 异步获取比赛记录
    public void getRecord__(String contestId,String Jsession, String ptaService) {
        new Thread(() -> {
            contestService.getRecord(contestId , Jsession, ptaService);
        }).start();
    }

    @PreDestroy
    public void shutdownScheduler() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
