package zzuli.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zzuli.pojo.entity.PTASession;
import zzuli.service.ContestService;
import zzuli.service.PTAService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    public void getRecord_(String contestId,java.sql.Timestamp endTime,String jsession, String ptaService) {
        Runnable task = () -> {
            // 如果现在的时间小于比赛结束时间再加五分钟
            if (System.currentTimeMillis() < endTime.getTime() + 300000) {
                contestService.getRecord(contestId , jsession, ptaService);
            } else {
                System.out.println("Stopping scheduled task: " + LocalDateTime.now());
                shutdownScheduler();
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
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
