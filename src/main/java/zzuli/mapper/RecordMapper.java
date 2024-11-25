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
    static void deleteAllBycontestId(String contestId) {

    }

    static void addRecord(Record record) {

    }

    int getNumByContestId(String contestId);

    List<Record> getRecordByContestId(String contestId);
}
