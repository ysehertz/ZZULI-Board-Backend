package zzuli.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Member;
import zzuli.pojo.entity.PTASession;
import zzuli.pojo.entity.Team;
import zzuli.pojo.vo.ContestListVO;

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

    void createContest(CreateContestDTO dto);

    void deleteContest(String contestId);

    void setContest(String contestId, CreateContestDTO dto);

    void setProblemList(String id, String problemList);

    List<Contest> getAllContest();

    PTASession getPTASession(String contestId);
}
