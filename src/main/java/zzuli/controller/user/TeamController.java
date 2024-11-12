package zzuli.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzuli.common.result.Result;
import zzuli.pojo.vo.TeamListVO;
import zzuli.service.TeamService;

import java.util.List;

/**
 * ClassName: TeamController
 * Package: zzuli.controller.user
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/list")
    public Result<List<TeamListVO>> list(@RequestParam(name = "contest_id") String contestId) {
        List<TeamListVO> teamVOList = teamService.getListByContestId(contestId);
        return Result.success(teamVOList);
    }
}
