package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.result.Result;
import zzuli.pojo.dto.SetMemberDTO;
import zzuli.service.MemberService;

/**
 * ClassName: MemberController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */

/**
 * 成员管理
 */
@RestController("adminMemberController")
@RequestMapping("/api/admin/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 设置成员信息
     * @param contestId
     * @param memberId
     * @param setMemberDTO
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setMember(@RequestParam(name = "contest_id") String contestId,
                            @RequestParam(name = "member_id") String memberId,
                            @RequestBody SetMemberDTO setMemberDTO) {
        setMemberDTO.setMember_id(memberId);
        setMemberDTO.setContest_id(contestId);
        memberService.setMember(setMemberDTO);

        return Result.success(null);
    }

    /**
     * 删除成员
     * @param contestId
     * @param memberId
     * @return
     */
    @PostMapping("/delete")
    public Result<Object> delMember(@RequestParam(name = "contest_id") String contestId,
                                    @RequestParam(name = "member_id") String memberId){
        memberService.delMember(contestId,memberId);

        return Result.success(null);
    }
}
