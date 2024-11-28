package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.SetMemberDTO;
import zzuli.pojo.dto.SetTeamDTO;
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

    void setMember(SetMemberDTO setMemberDTO);

    void delMember(String contestId, String memberId);

    /**
     * 根据队伍id删除队伍成员
     * @param teamId
     */
    void delMemberByTeamId(String teamId);

    void upRoomIdToNull(String roomId);

    void deleteMemberByContestID(String contestId);

    List<Member> list(String contestId);

    void setMemberFromTeam(SetTeamDTO setTeamDTO);
}
