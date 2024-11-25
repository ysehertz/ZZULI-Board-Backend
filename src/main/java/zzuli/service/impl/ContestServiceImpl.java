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
import zzuli.mapper.RecordMapper;
import zzuli.pojo.dto.CreateContestDTO;
import zzuli.pojo.dto.MemberDTO;
import zzuli.pojo.dto.RegisterTeamDTO;

import zzuli.pojo.dto.SingleDTO;
import zzuli.pojo.entity.*;
import zzuli.pojo.entity.Record;
import zzuli.pojo.vo.*;
import zzuli.service.ContestService;
import zzuli.service.PTAService;
import zzuli.utils.HttpClientUtil;

import java.sql.Timestamp;
import java.util.*;
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
    private RecordMapper recordMapper;
    @Autowired
    private PTAService ptaService;
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

    /**
     * 获取比赛记录
     * @param contestId
     * @return
     */
    @Override
    public List<RecordVO> record(String contestId) {
        Contest contest = contestMapper.getContestById(contestId);
        //redis模糊查询含该比赛记录的key
        Set<String> keys = redisTemplate.keys("C"+contestId+":*");
        List<RecordVO> teamList = new ArrayList<>();
        // 获取当前时间的时间戳
        Timestamp now = new Timestamp(System.currentTimeMillis());
        // 将比赛截止时间转为时间戳
        Timestamp end = contest.getEndTime();
        boolean isFrozen = contest.isFrozen()&&new Timestamp(now.getTime() + contest.getFrozenTime().getTime()).after(end);
        if(isFrozen){
            // 遍历哈希keys
            for(String key : keys ){
                // 获取key对应的value
                Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
                if(new Timestamp(Long.parseLong((String)values.get("submit_time")) + contest.getFrozenTime().getTime()).after(end)){
                    teamList.add(RecordVO.builder()
                            .record_id((String) values.get("record_id"))
                            .member_id((String) values.get("member_id"))
                            .problem_id((String) values.get("problem_id"))
                            .status("FROZEN")
                            .score((int) values.get("score"))
                            .language((String) values.get("language"))
                            .submit_time((Timestamp) values.get("submit_time"))
                            .balloon((Boolean) values.get("balloon"))
                            .build());
                }

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
        }

        return teamList;
    }

    /**
     * 队伍报名
     * @param contestId
     * @return
     */
    @Override
    public RegisterTeamVO registerTeam(String contestId, RegisterTeamDTO registerTeamDTO) {
        Contest contest = contestMapper.getContestById(contestId);
        // 校验当前时间是否在比赛报名时间内
        if (contest.getRegStartTime().after(new Timestamp(System.currentTimeMillis())) || contest.getRegEndTime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new RuntimeException(MessageConstant.REG_TIME_ERROR);
        }
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
                        .memberSchool(registerTeamDTO.getSchool())
                        .memberClass(registerTeamDTO.getClazz())
                        .memberCollege(registerTeamDTO.getCollage())
                        .official(team.isOfficial())
                        .build();
                contestMapper.saveMember(member);
            }
            return registerTeamVO;
        }
        throw new NoContestException(MessageConstant.NO_CONTEST);
    }

    /**
     * 个人报名
     * @param contestId
     * @param signalDTO
     */
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
        // 校验当前时间是否在比赛报名时间内
        if (contest.getRegStartTime().after(new Timestamp(System.currentTimeMillis())) || contest.getRegEndTime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new RuntimeException(MessageConstant.REG_TIME_ERROR);
        }
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

    /**
     * 创建比赛
     * @param dto
     */
    @Override
    public void CreateContest(CreateContestDTO dto) {
        Contest contest = Contest.builder()
                .contestId(dto.getId())
                .contestType(dto.getType())
                .PTASession(dto.getPTASession())
                .jsession(dto.getJsession())
                .title(dto.getTitle())
                .regType(dto.getReg_type())
                .regStartTime(dto.getReg_start_time())
                .regEndTime(dto.getReg_end_time())
                .regOffCode(dto.getReg_off_code())
                .regUnoffConde(dto.getReg_unoff_code())
                .banner(dto.getBanner())
                .penalty(dto.getPenalty())
                .frozen(dto.isFrozen())
                .frozenTime(new Timestamp(dto.getFrozen_time()))
                .balloonColor(dto.getBalloon_color().toString())
                .schoolList(dto.getSchool_list().toString())
                .build();
        contestMapper.createContest(dto);
    }

    /**
     * 删除比赛
     * @param contestId
     */
    @Override
    public void deleteContest(String contestId) {
        contestMapper.deleteContest(contestId);
    }

    /**
     * 修改比赛信息
     * @param contestId
     * @param dto
     */
    @Override
    public void setContest(String contestId, CreateContestDTO dto) {
        contestMapper.setContest(contestId, dto);
    }

    /**
     * 通过PTA更新比赛信息
     * @param dto 比赛信息
     */
    @Override
    public void UpContest(CreateContestDTO dto) {
       String getProblem = ptaService.getProblemList(dto);

       contestMapper.setProblemList(dto.getId(),getProblem);

       String result = ptaService.UpMemberId(dto.getId(),dto.getJsession(),dto.getPTASession(),"0");
       // 判断循环次数
       int cot = -1;

        try {
            JsonNode jsonNode = new ObjectMapper().readTree(result);
            int total = jsonNode.get("total").asInt();
            do {
                if(++cot > 0){
                    result = ptaService.UpMemberId(dto.getId(),dto.getJsession(),dto.getPTASession(),Integer.toString(cot));
                    jsonNode = new ObjectMapper().readTree(result);
                }
                JsonNode data = jsonNode.get("members");
                for (JsonNode node : data) {
                    String studentNumber = node.get("studentNumber").asText();
                    String id = node.get("id").asText();
                    //存到redis的map中，key为S+考场ID，value为studentNumber对应id
                    redisTemplate.opsForHash().put("S"+dto.getId(), studentNumber, id);
                }
            }while ((total -= 200) > 0);
        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.JSON_PARSE_ERROR);
        }
    }

    /**
     * 通过PTA获取测评纪录记录
     * @param contestId
     * @param jsession
     * @param PTASession
     */
    @Override
    public void getRecord(String contestId, String jsession, String PTASession) {
        String recordTab = (String)redisTemplate.opsForValue().getAndSet("R"+contestId, "0");
        if(recordTab == null || recordTab.equals("0"))
            RecordMapper.deleteAllBycontestId(contestId);
        Map<String,String> paramMap = new HashMap<>();
        String before = "0";
        String result = ptaService.getRecord(contestId, jsession, PTASession, before);

        // 继续增量标志
        boolean flag = true;
        // 每次获取数据的首数据标志
        int firstLab = 1;

        while (flag) {

            try {
                if(firstLab != 1){
                    result = ptaService.getRecord(contestId, jsession, PTASession, before);
                }
                JsonNode rootNode = objectMapper.readTree(result);
                JsonNode addressNode = rootNode.path("submissions");
                List<Record> recordList = objectMapper.readValue(addressNode.toString(), new TypeReference<List<Record>>() {});
                int size = recordList.size();
                if(size != 0){
                    before = recordList.get(size-1).getId();
                    for(Record record : recordList){
                        if(recordTab.equals(record.getId())){
                            flag = false;
                            break;
                        }
                        if(firstLab == 1){
                            redisTemplate.opsForValue().set("R"+contestId, record.getId());
                            firstLab = 0;
                        }
                        record.setMemberId((String)redisTemplate.opsForHash().get("S"+contestId, record.getUserId()));
                        RecordMapper.addRecord(record);


                        // 将数据存到redis当中
                        Map<String, Object> KVMap = new HashMap<>();
                        KVMap.put("record_id", record.getId());
                        KVMap.put("member_id", record.getMemberId());
                        KVMap.put("problem_id", record.getProblemSetProblemId());
                        KVMap.put("status", record.getStatus());
                        KVMap.put("score", record.getScore());
                        KVMap.put("language", record.getCompiler());
                        KVMap.put("submit_time", record.getSubmitAt());
                        KVMap.put("balloon", false);

                        redisTemplate.opsForHash().putAll("C"+contestId+":"+record.getId(), KVMap);
                    }
                }
                if(size < 200){
                    flag = false;
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(MessageConstant.JSON_PARSE_ERROR);
            }


        }


    }

    /**
     * 在定时任务中被调用，用于同步mysql和redis的数据（以mysql数据为准）
     * @param contestId
     * @return
     */
    @Override
    public void synchrodata(String contestId) {
        // 获取mysql数据数
        int num = recordMapper.getNumByContestId(contestId);
        // 获取redis数据数
        Set<String> keys = redisTemplate.keys("C"+contestId+":*");
        if(num != keys.size()){
            // 将mysql中的数据同步到redis
            List<Record> recordList = recordMapper.getRecordByContestId(contestId);
            for(Record record : recordList){
                Map<String, Object> KVMap = new HashMap<>();
                KVMap.put("record_id", record.getId());
                KVMap.put("member_id", record.getMemberId());
                KVMap.put("problem_id", record.getProblemSetProblemId());
                KVMap.put("status", record.getStatus());
                KVMap.put("score", record.getScore());
                KVMap.put("language", record.getCompiler());
                KVMap.put("submit_time", record.getSubmitAt());
                KVMap.put("balloon", false);
                redisTemplate.opsForHash().putAll("C"+contestId+":"+record.getId(), KVMap);
            }
        }
    }

    @Override
    public PTASession getPTASession(String contestId) {
        return contestMapper.getPTASession(contestId);
    }

}



