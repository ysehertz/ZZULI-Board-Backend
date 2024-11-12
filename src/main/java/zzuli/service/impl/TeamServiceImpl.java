package zzuli.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.mapper.TeamMapper;
import zzuli.pojo.entity.Team;
import zzuli.pojo.vo.TeamListVO;
import zzuli.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: TeamServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Override
    public List<TeamListVO> getListByContestId(String contestId) {
        List<Team> teamList = teamMapper.getListByContestId(contestId);
        if (teamList != null) {
            return teamList.stream().map(team -> TeamListVO.builder()
                    .id(team.getTeamId())
                    .name(team.getTeamName())
                    .coach(team.getTeamCoach())
                    .college(team.getTeamCollege())
                    .clazz(team.getTeamClass())
                    .official(team.isOfficial())
                    .build()).collect(Collectors.toList());
        }
        return null;
    }
}
