package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.entity.Record;

import java.util.List;

/**
 * ClassName: RecordMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/13
 */
@Mapper
public interface RecordMapper {
    void deleteAllBycontestId(String contestId);

    int getNumByContestId(String contestId);

    List<Record> getRecordByContestId(String contestId);

    void addRecord(Record record);

    void addRecords(List<Record> recordL);

    Record getRecordByContestIdAndMemberIdAndProblemId(String contestId, String studentNumber, String problemSetProblemId);

    void delcontestById(String id);

    void UpRecord(Record record);

    List<Record> getJudingRecord(String contestId);
}
