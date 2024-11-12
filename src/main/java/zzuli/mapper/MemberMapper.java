package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.SetMemberDTO;
import zzuli.pojo.entity.Member;

import java.util.List;

/**
 * ClassName: MemverMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Mapper
public interface MemberMapper {
    List<Member> list();

    void setMember(String contestId, String memberId, SetMemberDTO setMemberDTO);

    void delMember(String contestId, String memberId);

    /**
     * 根据队伍id删除队伍成员
     * @param teamId
     */
    void delMemberByTeamId(String teamId);

    void upRoomIdToNull(String roomId);

    void deleteMemberByContestID(String contestId);
}
