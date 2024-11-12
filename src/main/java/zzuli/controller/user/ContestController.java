package zzuli.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.result.Result;
import zzuli.pojo.dto.RegisterTeamDTO;

import zzuli.pojo.dto.SingleDTO;
import zzuli.pojo.vo.ContestListVO;
import zzuli.pojo.vo.ContestVO;
import zzuli.pojo.vo.RecordVO;
import zzuli.pojo.vo.RegisterTeamVO;
import zzuli.service.ContestService;

import java.util.List;

/**
 * ClassName: contestController
 * Package: zzuli.controller.user
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */

/**
 * 比赛信息
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class ContestController {
    @Autowired
    private ContestService contestService;

    /**
     * 获取比赛列表
     * @return
     */
    @GetMapping("/contest/list")
    public Result<List<ContestListVO>> list() {
        return Result.success(contestService.list());
    }

    /**
     * 获取比赛信息
     * @param contestId
     * @return
     */
    @GetMapping("/contest/config")
    public Result<ContestVO> config(@RequestParam(name = "contest_id") String contestId) {
        return Result.success(contestService.config(contestId));
    }

    /**
     * 获取比赛记录
     * @param contestId
     * @return
     */
    @GetMapping("/record")
    public Result<List<RecordVO>> record(@RequestParam(name = "contest_id") String contestId) {
        return Result.success(contestService.record(contestId));
    }

    /**
     * 注册队伍
     * @param contestId
     * @param registerTeamDTO
     * @return
     */
    @PostMapping("/register/team")
    public Result<RegisterTeamVO> registerTeam(@RequestParam(name = "contest_id") String contestId,
                                               @RequestBody RegisterTeamDTO registerTeamDTO) {
        return Result.success(contestService.registerTeam(contestId, registerTeamDTO));
    }

    /**
     * 个人注册
     * @param contestId
     * @param signalDTO
     * @return
     */
    @PostMapping("/register/signal")
    public Result<Integer> registerSignal(@RequestParam(name = "contest_id") String contestId,
                                          @RequestBody SingleDTO signalDTO) {
        contestService.registerSignal(contestId, signalDTO);
        return  Result.success(null);
    }
}

