package zzuli.controller.admin;

import com.sun.xml.bind.v2.TODO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Team;
import zzuli.pojo.vo.ContestAdminVO;
import zzuli.pojo.vo.ContestVO;
import zzuli.service.ContestService;
import zzuli.service.MemberService;
import zzuli.service.RoomService;
import zzuli.service.TeamService;

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
    private RedisTemplate redisTemplate;
    @Autowired
    private TeamService teamService;
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
        // 打印比赛开始时间
        log.info("比赛开始时间:{}",dto.getStart_time());
        // 创建比赛
        contestService.CreateContest(dto);
//        // 等待10s
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 更新比赛信息
//        contestService.UpContest(dto);
        // 更新测评记录
//        contestService.getRecord(dto.getId(),dto.getJsession(),dto.getPTASession());
        //异步更新比赛信息
        contestService.UpContestAsync(dto);
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
        //队伍
        teamService.delTeamFormContest(contestId);
        // 删除考场
        roomService.deleteRoomByContestID(contestId);
        //删除比赛记录
        contestService.deleteRecordByContestID(contestId);

        // 删除比赛
        contestService.deleteContest(contestId);
        return Result.success(null);
    }

    /**
     * 获取全部比赛信息
     * @param contestId
     * @return
     */
    @GetMapping("/config")
    public Result<ContestAdminVO> AdminConfigContest(@RequestParam(name = "contest_id") String contestId){

        return Result.success(contestService.AdminConfig(contestId));
    }

    /**
     * 修改比赛信息
     * @param contestId
     * @param dto
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setContest(
            @RequestParam(name = "contest_id") String contestId,
            @RequestBody CreateContestDTO dto){
        contestService.setContest(contestId,dto);

        // 更新比赛信息
        contestService.UpContest(dto);
        // 更新测评记录
        contestService.getRecord(dto.getId(),dto.getJsession(),dto.getPTASession());
        return Result.success(null);
    }
}
