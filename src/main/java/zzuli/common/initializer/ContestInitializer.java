package zzuli.common.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zzuli.common.Context.Judging;
import zzuli.mapper.ContestMapper;
import zzuli.mapper.RecordMapper;
import zzuli.service.ContestService;
import zzuli.task.GetRecordTask;

import javax.annotation.PostConstruct;

/**
 * ClassName: ContestInitializer
 * Package: zzuli.common.initializer
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/12/12
 */

@Component
public class ContestInitializer {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private GetRecordTask getRecordTask;
    @Autowired
    private ContestMapper contestMapper;
//    @Override
//    public void run(String... args) throws Exception {
//        contestMapper.getAllContest().forEach(contest -> {
//            if(contest.getEndTime().getTime() > System.currentTimeMillis() && contest.getStartTime().getTime() < System.currentTimeMillis()) {
//                System.out.println("jiosdjfiosdajfiodsjfiods");
//                recordMapper.getJudingRecord(contest.getContestId()).forEach(record -> {
//                    Judging.JUDGING.add(record.getId());
//                });
//                Judging.JUDGING.add("1865275184742424576");
//                getRecordTask.getRecord(contest.getContestId(), contest.getEndTime());
//            }
//        });
//    }
    @PostConstruct
    public void init() {
        contestMapper.getAllContest().forEach(contest -> {
            if(contest.getEndTime().getTime() > System.currentTimeMillis() && contest.getStartTime().getTime() < System.currentTimeMillis()) {
                recordMapper.getJudingRecord(contest.getContestId()).forEach(record -> {
                    Judging.JUDGING.add(record.getId());
                });
                getRecordTask.getRecord(contest.getContestId(), contest.getEndTime());
            }
        });
    }
}
