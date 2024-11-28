package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.Context.BaseContext;
import zzuli.common.result.Result;
import zzuli.mapper.MemberMapper;
import zzuli.pojo.dto.SetTeamDTO;
import zzuli.service.MemberService;
import zzuli.service.TeamService;

/**
 * ClassName: TeamController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */
/**
 * 队伍管理
 */
@RestController("adminTeamController")
@RequestMapping("/api/admin/team")
@Slf4j
public class TeamController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamService teamService;

    /**
     * 设置队伍信息
     * @param teamId
     * @param setTeamDTO
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setTeam(@RequestParam(name = "team_id") String teamId,
                                   @RequestBody SetTeamDTO setTeamDTO) {
        setTeamDTO.setTeamId(teamId);
        teamService.setTeam(setTeamDTO);
        log.info("修改队伍信息;管理员：{}，队伍ID：{}", BaseContext.getCurrentId(),teamId);
        return Result.success(null);
    }

    /**
     * 删除队伍
     * @param teamId
     * @return
     */
    @PostMapping("/delete")
    public Result<Object> delTeam(@RequestParam(name = "team_id") String teamId){

        teamService.delTeam(teamId);
        log.info("删除队伍;管理员：{}，队伍ID：{}", BaseContext.getCurrentId(),teamId);
        return Result.success(null);
    }


}
