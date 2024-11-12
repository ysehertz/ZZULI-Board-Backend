package zzuli.service;

import zzuli.pojo.dto.SetTeamDTO;
import zzuli.pojo.vo.TeamListVO;

import java.util.List;

/**
 * ClassName: TeamService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
public interface TeamService {
    List<TeamListVO> getListByContestId(String contestId);

    void setTeam(String teamId, SetTeamDTO setTeamDTO);

    void delTeam(String teamId);
}
