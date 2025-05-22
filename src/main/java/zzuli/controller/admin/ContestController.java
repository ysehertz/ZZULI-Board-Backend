package zzuli.controller.admin;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.sun.xml.bind.v2.TODO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.common.Context.BaseContext;
import zzuli.common.result.Result;
import zzuli.mapper.ContestMapper;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.pojo.entity.Contest;
import zzuli.pojo.entity.Team;
import zzuli.pojo.vo.ContestAdminVO;
import zzuli.pojo.vo.ContestVO;
import zzuli.service.ContestService;
import zzuli.service.MemberService;
import zzuli.service.RoomService;
import zzuli.service.TeamService;

import java.sql.Timestamp;
import java.time.Instant;

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
    private ContestMapper contestMapper;

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
        log.info("创建比赛;管理员：{}，比赛名：{}",BaseContext.getCurrentId(),dto.getTitle());
        // 创建比赛
        contestService.CreateContest(dto);

        //异步更新比赛信息
        contestService.UpContestAsync(dto);
        log.info("创建比赛成功;管理员：{}，比赛名：{}",BaseContext.getCurrentId(), dto.getTitle());
        return  Result.success(null);
    }

    /**
     * 删除比赛
     * @param contestId
     * @return
     */

    @PostMapping("/delete")
    public Result<Integer> deleteContest(@RequestParam(name = "contest_id") String contestId) {
        log.info("删除比赛;管理员：{}，比赛名：{}",BaseContext.getCurrentId(),contestId);
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
        log.info("删除比赛成功;管理员：{}，比赛名：{}",BaseContext.getCurrentId(),contestId);
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
        log.info("修改比赛;管理员：{}，比赛名：{}",BaseContext.getCurrentId(), contestId);
        contestService.setContest(contestId,dto);
        //异步更新比赛信息
        contestService.UpContestAsync(dto);
        log.info("修改比赛成功;管理员：{}，比赛名：{}",BaseContext.getCurrentId(),contestId);
        return Result.success(null);
    }

    /**
     * 重新启动获取记录线程
     * @param contestId
     * @return
     */
    @PostMapping("/flush")
    public Result<Integer> flushContest(@RequestParam(name = "contest_id") String contestId){
        log.info("重新获取比赛信息;管理员：{}，比赛名：{}",BaseContext.getCurrentId(), contestId);
        Contest contest = contestMapper.getContestById(contestId);
        // 如果比赛已经结束
        Timestamp endTime =  contestMapper.getContestById(contestId).getEndTime();
        if (endTime.getTime() < Instant.now().toEpochMilli()){
            contestService.getRecord(contestId,contest.getJsession(),contest.getPTASession());
        }else {
            contestService.flushContest(contestId);
        }
        return Result.success(null);
    }

    /**
     * 重新获取比赛题目
     * @param contestId
     * @return
     */
    @PostMapping("/flushProblem")
    public Result<Integer> flushProblem(@RequestParam(name = "contest_id") String contestId){
        log.info("重新获取比赛题目;管理员：{}，比赛名：{}",BaseContext.getCurrentId(), contestId);
        contestService.flushProblem(contestId);
        return Result.success(null);
    }

}
