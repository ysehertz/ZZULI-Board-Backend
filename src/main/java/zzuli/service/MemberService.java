package zzuli.service;

import zzuli.pojo.dto.SetMemberDTO;
import zzuli.pojo.vo.MemberListVO;

import java.util.List;

/**
 * ClassName: MemberService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
public interface MemberService {

    List<MemberListVO> list();

    void setMember(String contestId, String memberId, SetMemberDTO setMemberDTO);

    void delMember(String contestId, String memberId);

    void deleteMemberByContestID(String contestId);
}
