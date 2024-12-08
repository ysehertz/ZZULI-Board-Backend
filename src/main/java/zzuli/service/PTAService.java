package zzuli.service;

import zzuli.pojo.dto.CreateContestDTO;

/**
 * ClassName: PTAService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/13
 */

public interface PTAService {
    // 获取比赛列表
     String  getProblemList(String contestId, String Jsession, String PTASession);
     // 获取考生信息(考生ptaId与学号的对应关系)
     String UpMemberId(String contestId, String Jsession, String PTASession,String page);

    String getRecord(String contestId, String Jsession, String ptaSession, String before);



}
