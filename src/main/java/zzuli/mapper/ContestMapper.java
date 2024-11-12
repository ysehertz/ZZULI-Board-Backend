package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zzuli.common.result.Result;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Member;
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
}
