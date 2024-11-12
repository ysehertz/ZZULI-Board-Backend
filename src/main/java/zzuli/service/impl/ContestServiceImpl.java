package zzuli.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zzuli.common.constant.MessageConstant;
import zzuli.common.exception.NoContestException;
import zzuli.common.result.Result;
import zzuli.mapper.ContestMapper;
import zzuli.pojo.dto.MemberDTO;
import zzuli.pojo.dto.RegisterTeamDTO;

import zzuli.pojo.dto.SingleDTO;
import zzuli.pojo.entity.*;
import zzuli.pojo.vo.*;
import zzuli.service.ContestService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: ContestService
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Slf4j
@Service
public class ContestServiceImpl implements ContestService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ContestMapper contestMapper;
    /**
     * 获取比赛列表
     * @return
     */
    @Override
    public List<ContestListVO> list() {
        List<Contest> contests = contestMapper.list();
        if (contests != null) {
            return contests.stream().map(contest -> ContestListVO.builder()
                    .id(contest.getContestId())
                    .type(contest.getContestType())
                    .title(contest.getTitle())
                    .start_time(contest.getStartTime())
                    .end_time(contest.getEndTime())
                    .reg_type(contest.getRegType())
                    .reg_start_time(contest.getRegStartTime())
                    .reg_end_time(contest.getRegEndTime())
                    .build()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 根据比赛id获取比赛信息
     * @param contestId
     * @return
     */
    @Override
    public ContestVO config(String contestId) {
        Contest contest = contestMapper.getContestById(contestId);


        if (contest != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            List<Balloon> balloonsList = null;
            List<School> schoolList = null;
            List<Problem> problemList = null;
            try {
                rootNode = objectMapper.readTree(contest.getBalloonColor());
                balloonsList = objectMapper.readValue(objectMapper.readTree(contest.getBalloonColor()).toString(), new TypeReference<List<Balloon>>() {});
                schoolList = objectMapper.readValue(objectMapper.readTree(contest.getSchoolList()).toString(), new TypeReference<List<School>>() {});
                problemList = objectMapper.readValue(objectMapper.readTree(contest.getProblemList()).toString(), new TypeReference<List<Problem>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return ContestVO.builder()
                    .id(contest.getContestId())
                    .type(contest.getContestType())
                    .title(contest.getTitle())
                    .banner(contest.getBanner())
                    .start_time(contest.getStartTime())
                    .end_time(contest.getEndTime())
                    .reg_type(contest.getRegType())
                    .reg_start_time(contest.getRegStartTime())
                    .reg_end_time(contest.getRegEndTime())
                    .penalty(contest.getPenalty())
                    .frozen(contest.isFrozen())
                    .frozen_time(contest.getFrozenTime())
                    .balloon_color(balloonsList)
                    .problem_list(problemList)
                    .school_list(schoolList)
                    .build();
        }
        return null;
    }

    @Override
    public List<RecordVO> record(String contestId) {

        //redis模糊查询含该比赛记录的key
        Set<String> keys = redisTemplate.keys("C"+contestId+"*");
        List<RecordVO> teamList = new ArrayList<>();
        // 遍历哈希keys
        for(String key : keys){
            // 获取key对应的value
            Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
            teamList.add(RecordVO.builder()
                    .record_id((String) values.get("record_id"))
                    .member_id((String) values.get("member_id"))
                    .problem_id((String) values.get("problem_id"))
                    .status((String) values.get("status"))
                    .score((int) values.get("score"))
                    .language((String) values.get("language"))
                    .submit_time((Timestamp) values.get("submit_time"))
                    .balloon((Boolean) values.get("balloon"))
                    .build());
        }
        return teamList;
    }

    @Override
    public RegisterTeamVO registerTeam(String contestId, RegisterTeamDTO registerTeamDTO) {
        Contest contest = contestMapper.getContestById(contestId);
        Team team = Team.builder()
                .teamName(registerTeamDTO.getName())
                .teamCoach(registerTeamDTO.getCoach())
                .teamSchool(registerTeamDTO.getSchool())
                .teamClass(registerTeamDTO.getClazz())
                .build();
        if (contest != null) {
            //校验邀请码
            if (contest.getRegOffCode().equals(registerTeamDTO.getReg_code())) {
                // 校内成员设置为正式队伍
                team.setOfficial(true);
            } else if (contest.getRegUnoffConde().equals(registerTeamDTO.getReg_code())) {
                // 校外成员设置为非正式队伍
                team.setOfficial(false);
            } else {
                throw new RuntimeException(MessageConstant.REG_CODE_ERROR);
            }
            // 保存队伍信息
            contestMapper.saveTeam(team);
            RegisterTeamVO registerTeamVO = RegisterTeamVO.builder()
                    .team_id(team.getTeamId())
                    .build();
            // 保存个人信息
            for (MemberDTO memberDTO : registerTeamDTO.getMembers()) {
                Member member = Member.builder()
                        .teamId(team.getTeamId())
                        .memberName(memberDTO.getName())
                        .contestId(contestId)
                        .memberId(memberDTO.getId())
                        .memberSchool(memberDTO.getSchool())
                        .memberClass(memberDTO.getClazz())
                        .official(team.isOfficial())
                        .build();
                contestMapper.saveMember(member);
            }
            return registerTeamVO;
        }
        throw new NoContestException(MessageConstant.NO_CONTEST);
    }

    @Override
    public void registerSignal(String contestId, SingleDTO signalDTO) {
        Contest contest = contestMapper.getContestById(contestId);
        Member member = Member.builder()
                .memberId(signalDTO.getId())
                .memberName(signalDTO.getName())
                .contestId(contestId)
                .memberCollege(signalDTO.getCollege())
                .memberSchool(signalDTO.getSchool())
                .memberClass(signalDTO.getClazz())
                .build();
        if (contest != null) {
            //校验邀请码
            if (contest.getRegOffCode().equals(signalDTO.getReg_code())) {
                // 校内成员设置为正式队伍
                member.setOfficial(true);
            } else if (contest.getRegUnoffConde().equals(signalDTO.getReg_code())) {
                // 校外成员设置为非正式队伍
                member.setOfficial(false);
            } else {
                throw new RuntimeException(MessageConstant.REG_CODE_ERROR);
            }
            // 保存个人信息
            contestMapper.saveMember(member);
        }
        throw new NoContestException(MessageConstant.NO_CONTEST);
    }
}
