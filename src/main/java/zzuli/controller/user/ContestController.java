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
@Slf4j
@RequestMapping("/api")
@RestController
public class ContestController {
    @Autowired
    private ContestService contestService;

    @GetMapping("/contest/list")
    public Result<List<ContestListVO>> list() {
        return Result.success(contestService.list());
    }

    @GetMapping("/contest/config")
    public Result<ContestVO> config(@RequestParam(name = "contest_id") String contestId) {
        return Result.success(contestService.config(contestId));
    }

    @GetMapping("/record")
    public Result<List<RecordVO>> record(@RequestParam(name = "contest_id") String contestId) {
        return Result.success(contestService.record(contestId));
    }

    @PostMapping("/register/team")
    public Result<RegisterTeamVO> registerTeam(@RequestParam(name = "contest_id") String contestId,
                                               @RequestBody RegisterTeamDTO registerTeamDTO) {
        return Result.success(contestService.registerTeam(contestId, registerTeamDTO));
    }

    @PostMapping("/register/siganl")
    public Result<Integer> registerSignal(@RequestParam(name = "contest_id") String contestId,
                                          @RequestBody SingleDTO signalDTO) {
        contestService.registerSignal(contestId, signalDTO);
        return  Result.success(null);
    }
}

