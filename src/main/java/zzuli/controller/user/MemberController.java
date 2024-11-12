package zzuli.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzuli.common.result.Result;
import zzuli.pojo.vo.MemberListVO;
import zzuli.service.MemberService;

import java.util.List;

/**
 * ClassName: MemberController
 * Package: zzuli.controller.user
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Slf4j
@RequestMapping("/api/member")
@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public Result<List<MemberListVO>> list(@RequestParam(name = "contest_id") String contestId) {
        List<MemberListVO> memberVOList = memberService.list();
        return Result.success(memberVOList);
    }
}
