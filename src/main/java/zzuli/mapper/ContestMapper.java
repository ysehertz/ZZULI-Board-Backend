package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Member;
import zzuli.pojo.pta.PTASession;
import zzuli.pojo.entity.Team;

import java.util.List;

/**
 * ClassName: ContestMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Mapper
public interface ContestMapper {

    List<Contest> list();

    Contest getContestById(String contestId);

    void saveTeam(Team team);


    void saveMember(Member member);

    void createContest(Contest contest);

    void deleteContest(String contestId);

    void setContest(Contest contest);

    void setProblemList(String id, String problemList);

    List<Contest> getAllContest();

    PTASession getPTASession(String contestId);
}
