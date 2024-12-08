package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.mapper.MemberMapper;
import zzuli.pojo.dto.SetMemberDTO;
import zzuli.pojo.entity.Member;
import zzuli.pojo.vo.MemberListVO;
import zzuli.service.MemberService;

import java.util.List;

/**
 * ClassName: MemberServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired()
    private MemberMapper memberMapper;

    @Override
    public List<MemberListVO> list(String contestId) {
        List<Member> members = memberMapper.list(contestId);
        if (members != null) {
            return members.stream().map(member -> MemberListVO.builder()
                    .qq(member.getMemberQq())
                    .phone(member.getMemberPhone())
                    .team_id(member.getTeamId())
                    .room_id(member.getRoomId())
                    .member_id(member.getMemberId())
                    .name(member.getMemberName())
                    .school(member.getMemberSchool())
                    .college(member.getMemberCollege())
                    .clazz(member.getMemberClass())
                    .official(member.isOfficial())
                    .build()).collect(java.util.stream.Collectors.toList());
        }
        return null;
    }

    @Override
    public void setMember( SetMemberDTO setMemberDTO) {
        memberMapper.setMember(setMemberDTO);
    }

    @Override
    public void delMember(String contestId, String memberId) {
        memberMapper.delMember(contestId, memberId);
    }

    @Override
    public void deleteMemberByContestID(String contestId) {
        memberMapper.deleteMemberByContestID(contestId);
    }
}
