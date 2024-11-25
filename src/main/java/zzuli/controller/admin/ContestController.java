package zzuli.controller.admin;

import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.service.ContestService;
import zzuli.service.MemberService;
import zzuli.service.RoomService;

/**
 * ClassName: ContestController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */

/**
 * 比赛管理
 */
@RestController("adminContestController")
@RequestMapping("/api/admin/contest")
@Slf4j
public class ContestController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ContestService contestService;

    /**
     * 创建比赛
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public Result<Integer> CreateContest(@RequestBody CreateContestDTO dto){
        // 创建比赛
        contestService.CreateContest(dto);
        // 更新比赛信息
        contestService.UpContest(dto);
        return  Result.success(null);
    }

    /**
     * 删除比赛
     * @param contestId
     * @return
     */

    @PostMapping("/delete")
    public Result<Integer> deleteContest(@RequestParam(name = "contest_id") String contestId) {
        // 删除学生
        memberService.deleteMemberByContestID(contestId);
        // 删除考场
        roomService.deleteRoomByContestID(contestId);
        // 删除比赛
        contestService.deleteContest(contestId);
        return Result.success(null);
    }

    @PostMapping("/set")
    public Result<Integer> setContest(
            @RequestParam(name = "contest_id") String contestId,
            @RequestBody CreateContestDTO DTO){
        contestService.setContest(contestId,DTO);
        return Result.success(null);
    }
}
