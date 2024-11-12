package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.entity.Team;

import java.util.List;

/**
 * ClassName: TeamaMaper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Mapper
public interface TeamMapper {
    List<Team> getListByContestId(String contestId);

    void setTeam(Team team);

    void delTeam(String teamId);
}
