package zzuli.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzuli.common.result.Result;
import zzuli.pojo.vo.RoomVO;
import zzuli.service.RoomService;

import java.util.List;

/**
 * ClassName: RoomController
 * Package: zzuli.controller.user
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping("/list")
    public Result<List<RoomVO>> list(@RequestParam(name = "contest_id") String contestId) {
        return Result.success(roomService.list(contestId));
    }
}
