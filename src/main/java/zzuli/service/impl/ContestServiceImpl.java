package zzuli.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zzuli.common.constant.MessageConstant;
import zzuli.common.exception.BaseException;
import zzuli.common.exception.NoContestException;
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

import java.sql.Time;
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
                    .start_time(contest.getStartTime().toInstant().getEpochSecond())
                    .end_time(contest.getEndTime().toInstant().getEpochSecond())
                    .reg_type(contest.getRegType())
                    .reg_start_time(contest.getRegStartTime().toInstant().getEpochSecond())
                    .reg_end_time(contest.getRegEndTime().toInstant().getEpochSecond())
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
            List<ProblemVO> problemList = null;
            try {
//                rootNode = objectMapper.readTree(contest.getBalloonColor());
                balloonsList = objectMapper.readValue(objectMapper.readTree(contest.getBalloonColor()).toString(), new TypeReference<List<Balloon>>() {});
                problemList = objectMapper.readValue(objectMapper.readTree(contest.getProblemList()).toString(), new TypeReference<List<ProblemVO>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode extra = null;
            try {
                extra = objectMapper.readTree(contest.getExtra());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return ContestVO.builder()
                    .id(contest.getContestId())
                    .type(contest.getContestType())
                    .title(contest.getTitle())
                    .start_time(contest.getStartTime().toInstant().getEpochSecond())
                    .end_time(contest.getEndTime().toInstant().getEpochSecond())
                    .reg_type(contest.getRegType())
                    .reg_start_time(contest.getRegStartTime().toInstant().getEpochSecond())
                    .reg_end_time(contest.getRegEndTime().toInstant().getEpochSecond())
                    .extra(extra)
                    .balloon_color(balloonsList)
                    .problem_list(problemList)
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
        String type = contest.getContestType();
        //redis模糊查询含该比赛记录的key
        Set<String> keys = redisTemplate.keys("C"+contestId+":*");
        List<RecordVO> teamList = new ArrayList<>();
        if(type.equals("ccpc")){
            // 获取现在时间的时间戳
            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 将比赛截止时间转为时间戳
            Timestamp end = contest.getEndTime();
            ICPCContestExtraData icpcContestExtraData = null;
            try {
                JsonNode jsonNode =  objectMapper.readTree(contest.getExtra());
                icpcContestExtraData = objectMapper.readValue(jsonNode.toString(), ICPCContestExtraData.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            boolean isFrozen = icpcContestExtraData.isFrozen()&& end.after(now) && new Timestamp(now.getTime() + icpcContestExtraData.getFrozenTime()).after(end);
            if(isFrozen){
                // 遍历哈希keys
                for(String key : keys ){
                    // 获取key对应的value
                    Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
                    // 检查 score 是否为 null，并提供默认值
                    String scoreStr = (String) values.get("score");
                    Integer score =  0; // 默认值为 0
                    if (scoreStr != null) {
                        try {
                            // 尝试将字符串转换为 Double
                            double scoreDouble = Double.parseDouble(scoreStr);
                            // 将 Double 转换为 Integer
                            score = (int) scoreDouble;
                        } catch (NumberFormatException e) {
                            // 处理转换失败的情况
                            score = 0;
                        }
                    }

                    if(new Timestamp(Long.parseLong((String)values.get("submit_time")) + icpcContestExtraData.getFrozenTime()).after(end)){
                        teamList.add(RecordVO.builder()
                                .record_id((String) values.get("record_id"))
                                .member_id((String) values.get("member_id"))
                                .problem_id((String) values.get("problem_id"))
                                .status("FROZEN")
                                .score(score)
                                .language((String) values.get("language"))
                                .submit_time(((Timestamp) values.get("submit_time")).toInstant().getEpochSecond())
                                .balloon((Boolean) values.get("balloon"))
                                .build());
                    }

                    teamList.add(RecordVO.builder()
                            .record_id((String) values.get("record_id"))
                            .member_id((String) values.get("member_id"))
                            .problem_id((String) values.get("problem_id"))
                            .status((String) values.get("status"))
                            .score(score)
                            .language((String) values.get("language"))
                            .submit_time(((Timestamp) values.get("submit_time")).toInstant().getEpochSecond())
                            .balloon((Boolean) values.get("balloon"))
                            .build());
                }
            }else{
                // 遍历哈希keys
                for(String key : keys ){
                    // 获取key对应的value
                    Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
                    // 检查 score 是否为 null，并提供默认值
                    String scoreStr = (String) values.get("score");
                    Integer score =  0; // 默认值为 0
                    if (scoreStr != null) {
                        try {
                            // 尝试将字符串转换为 Double
                            double scoreDouble = Double.parseDouble(scoreStr);
                            // 将 Double 转换为 Integer
                            score = (int) scoreDouble;
                        } catch (NumberFormatException e) {
                            // 处理转换失败的情况
                            score = 0;
                        }
                    }

                    teamList.add(RecordVO.builder()
                            .record_id((String) values.get("record_id"))
                            .member_id((String) values.get("member_id"))
                            .problem_id((String) values.get("problem_id"))
                            .status((String) values.get("status"))
                            .score(score)
                            .language((String) values.get("language"))
                            .submit_time(((Timestamp) values.get("submit_time")).toInstant().getEpochSecond())
                            .balloon((Boolean) values.get("balloon"))
                            .build());
                }
            }
        }else if(type.equals("gplt")){
            // 遍历哈希keys
            for(String key : keys ){
                // 获取key对应的value
                Map<Object, Object> values = redisTemplate.opsForHash().entries(key);
                // 检查 score 是否为 null，并提供默认值
                String scoreStr = (String) values.get("score");
                Integer score =  0; // 默认值为 0
                if (scoreStr != null) {
                    try {
                        // 尝试将字符串转换为 Double
                        double scoreDouble = Double.parseDouble(scoreStr);
                        // 将 Double 转换为 Integer
                        score = (int) scoreDouble;
                    } catch (NumberFormatException e) {
                        // 处理转换失败的情况
                        score = 0;
                    }
                }
                teamList.add(RecordVO.builder()
                        .record_id((String) values.get("record_id"))
                        .member_id((String) values.get("member_id"))
                        .problem_id((String) values.get("problem_id"))
                        .status((String) values.get("status"))
                        .score(score)
                        .language((String) values.get("language"))
                        .submit_time(((Timestamp) values.get("submit_time")).toInstant().getEpochSecond())
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
                .contestId(contestId)
                .teamName(registerTeamDTO.getName())
                .teamCoach(registerTeamDTO.getCoach())
                .teamSchool(registerTeamDTO.getSchool())
                .teamClass(registerTeamDTO.getClazz())
                .teamCollege(registerTeamDTO.getCollege())
                .build();
        if (contest != null) {
            //校验邀请码
            if (contest.getRegOffCode().equals(registerTeamDTO.getReg_code())) {
                // 校内成员设置为正式队伍
                team.setOfficial(true);
            } else if (contest.getRegUnoffCode().equals(registerTeamDTO.getReg_code())) {
                // 校外成员设置为非正式队伍
                team.setOfficial(false);
            } else {
                throw new BaseException(MessageConstant.REG_CODE_ERROR);
            }
            // 保存队伍信息
            contestMapper.saveTeam(team);
            RegisterTeamVO registerTeamVO = RegisterTeamVO.builder()
                    .team_id(team.getTeamId())
                    .build();
            // 保存个人信息
            List<MemberDTO> memberDTOS = registerTeamDTO.getMembers();
            for (MemberDTO memberDTO : memberDTOS) {
                Member member = Member.builder()
                        .teamId(team.getTeamId())
                        .memberName(memberDTO.getName())
                        .contestId(contestId)
                        .memberId(memberDTO.getId())
                        .memberSchool(registerTeamDTO.getSchool())
                        .memberClass(registerTeamDTO.getClazz())
                        .memberCollege(registerTeamDTO.getCollege())
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
        if(contest == null){
            throw new NoContestException(MessageConstant.NO_CONTEST);
        }
        // 校验当前时间是否在比赛报名时间内
        if (contest.getRegStartTime().after(new Timestamp(System.currentTimeMillis())) || contest.getRegEndTime().before(new Timestamp(System.currentTimeMillis()))) {
            throw new BaseException(MessageConstant.REG_TIME_ERROR);
        }
        //校验邀请码
        if (contest.getRegOffCode().equals(signalDTO.getReg_code())) {
            // 校内成员设置为正式队伍
            member.setOfficial(true);
        } else if (contest.getRegUnoffCode().equals(signalDTO.getReg_code())) {
            // 校外成员设置为非正式队伍
            member.setOfficial(false);
        } else {
            throw new BaseException(MessageConstant.REG_CODE_ERROR);
        }
        // 保存个人信息
        contestMapper.saveMember(member);
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
                .Jesession(dto.getJsession())
                .title(dto.getTitle())
                .startTime(Timestamp.from(dto.getStart_time()))
                .endTime(Timestamp.from(dto.getEnd_time()))
                .regType(dto.getReg_type())
                .regStartTime(Timestamp.from(dto.getReg_start_time()))
                .regEndTime(Timestamp.from(dto.getReg_end_time()))
                .regOffCode(dto.getReg_off_code())
                .regUnoffCode(dto.getReg_unoff_code())
                .extra(dto.getExtra().toString())
                .balloonColor(dto.getBalloon_color().toString())
                .build();
        contestMapper.createContest(contest);

        if(dto.getEnd_time().toEpochMilli()> System.currentTimeMillis()){
            redisTemplate.opsForHash().put("notBegin", dto.getId(), contest.getStartTime().getTime()+"="+contest.getEndTime().getTime());
        }
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
        Contest contest = Contest.builder()
                .contestId(contestId)
                .contestType(dto.getType())
                .PTASession(dto.getPTASession())
                .Jesession(dto.getJsession())
                .title(dto.getTitle())
                .startTime(Timestamp.from(dto.getStart_time()))
                .endTime(Timestamp.from(dto.getEnd_time()))
                .regType(dto.getReg_type())
                .regStartTime(Timestamp.from(dto.getReg_start_time()))
                .regEndTime(Timestamp.from(dto.getReg_end_time()))
                .regOffCode(dto.getReg_off_code())
                .regUnoffCode(dto.getReg_unoff_code())
                .extra(dto.getExtra().toString())
                .balloonColor(dto.getBalloon_color().toString())
                .build();
        contestMapper.setContest(contest);
    }

    /**
     * 通过PTA更新比赛信息
     * @param dto 比赛信息
     */
    @Override
    public void UpContest(CreateContestDTO dto) {

       String getProblem = ptaService.getProblemList(dto);
       if(!getProblem.isEmpty()){
           try {
               JsonNode jsonNode = new ObjectMapper().readTree(getProblem);
               JsonNode data = jsonNode.get("problemSetProblems");
               List<Problem> problemList = objectMapper.readValue(data.toString(), new TypeReference<List<Problem>>() {});
               List<ProblemVO> problemVOS =problemList.stream().map(problem -> ProblemVO.builder()
                       .id(problem.getId())
                       .score(problem.getScore())
                       .name(problem.getName())
                       .build()).collect(Collectors.toList());
               getProblem = objectMapper.writeValueAsString(problemVOS);
           } catch (JsonProcessingException e) {
               throw new RuntimeException(e);
           }
       }else{
           getProblem = "{}";
       }
        contestMapper.setProblemList(dto.getId(),getProblem);

       String result = ptaService.UpMemberId(dto.getId(),dto.getJsession(),dto.getPTASession(),"0");
       if(!result.isEmpty()){
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
                       String studentNumber = node.get("studentUser").get("studentNumber").asText();
                       String id = node.get("user").get("id").asText();
                       //存到redis的map中，key为S+考场ID，value为studentNumber对应id
                       redisTemplate.opsForHash().put("S"+dto.getId(),id, studentNumber);
                   }
               }while ((total -= 200) > 0);
           } catch (Exception e) {
               throw new RuntimeException(MessageConstant.JSON_PARSE_ERROR);
           }
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
        if(recordTab == null || recordTab.equals("0")){
            recordTab = "0";
            recordMapper.deleteAllBycontestId(contestId);
        }
        Map<String,String> paramMap = new HashMap<>();
        String before = "0";
        String result = ptaService.getRecord(contestId, jsession, PTASession, before);

//        List<Record> recordL = new ArrayList<>();
        if(!result.isEmpty()){

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
                            if( recordTab.equals(record.getId())){
                                flag = false;
                                break;
                            }
                            if(firstLab == 1){
                                redisTemplate.opsForValue().set("R"+contestId, record.getId());
                                firstLab = 0;
                            }
                            //获取redis中S+考场ID的map类型中key为record.getUserId()的value
                            String studentNumber = (String) redisTemplate.opsForHash().get("S"+contestId, record.getUserId());
//                            String studentNumber = (String) redisTemplate.opsForHash().get("S"+contestId, record.getUserId());
                            record.setMemberId(studentNumber);
                            record.setContestId(contestId);
                            if(studentNumber != null){

                                recordMapper.addRecord(record);

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

                    }
                    if(size < 200){
                        flag = false;
                    }

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(MessageConstant.JSON_PARSE_ERROR);
                }


            }
        }
//        recordMapper.addRecords(recordL);


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

    /**
     * 管理员获取比赛信息
     * @param contestId
     * @return
     */
    @Override
    public ContestAdminVO AdminConfig(String contestId) {
        Contest contest = contestMapper.getContestById(contestId);
        if (contest != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            List<Balloon> balloonsList = null;
            List<Problem> problemList = null;
            try {
                rootNode = objectMapper.readTree(contest.getBalloonColor());
                balloonsList = objectMapper.readValue(rootNode.toString(), new TypeReference<List<Balloon>>() {});
                problemList = objectMapper.readValue(contest.getProblemList(), new TypeReference<List<Problem>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode extra = null;
            try {
                extra = objectMapper.readTree(contest.getExtra());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return ContestAdminVO.builder()
                    .contestId(contest.getContestId())
                    .PTASession(contest.getPTASession())
                    .jesession(contest.getJesession())
                    .contestType(contest.getContestType())
                    .title(contest.getTitle())
                    .startTime(contest.getStartTime().toInstant().getEpochSecond())
                    .endTime(contest.getEndTime().toInstant().getEpochSecond())
                    .regType(contest.getRegType())
                    .regStartTime(contest.getRegStartTime().toInstant().getEpochSecond())
                    .regEndTime(contest.getRegEndTime().toInstant().getEpochSecond())
                    .regOffCode(contest.getRegOffCode())
                    .regUnoffConde(contest.getRegUnoffCode())
                    .extra(extra)
                    .balloonColor(balloonsList)
                    .problemList(problemList)
                    .build();
        }
        return null;
    }

    /**
     * 删除评测记录
     * @param contestId
     */
    @Override
    public void deleteRecordByContestID(String contestId) {
        recordMapper.deleteAllBycontestId(contestId);
        redisTemplate.keys("C"+contestId+":*").forEach(key -> redisTemplate.delete(key));
        redisTemplate.delete("R"+contestId);
        redisTemplate.delete("S"+contestId);
    }

    /**
     * 异步获取测评记录
     * @param id
     * @param jsession
     * @param ptaSession
     */
    @Override
    public void getRecordAsync(String id, String jsession, String ptaSession) {
        new Thread(() -> {
            getRecord(id, jsession, ptaSession);
        }).start();
    }

    @Override
    public void UpContestAsync(CreateContestDTO dto) {
        new Thread(() -> {
            UpContest(dto);
            getRecord(dto.getId(),dto.getJsession(),dto.getPTASession());
        }).start();
    }

}



