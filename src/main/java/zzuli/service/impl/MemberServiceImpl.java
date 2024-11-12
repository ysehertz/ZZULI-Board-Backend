package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.mapper.MemberMapper;
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
    public List<MemberListVO> list() {
        List<Member> members = memberMapper.list();
        if (members != null) {
            return members.stream().map(member -> MemberListVO.builder()
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
}
