package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.result.Result;
import zzuli.pojo.dto.SetRoomDTO;
import zzuli.service.RoomService;

/**
 * ClassName: RoomController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */

/**
 * 考场管理
 */
@RestController("adminRoomController")
@RequestMapping("/api/admin/room")
@Slf4j
public class RoomController {
    @Autowired
    private RoomService roomService;
    @PostMapping("/delete")
    public Result<Integer> deleteRoom(@RequestParam(name = "room_id") String roomId) {
        roomService.deleteRoom(roomId);
        return Result.success(null);
    }

    /**
     * 设置考场信息
     * @param roomId
     * @return
     */
    @PostMapping("/set")
    public Result<Object> setRoom(@RequestParam(name = "room_id") String roomId,
                                  @RequestBody SetRoomDTO setRoomDTO) {
        roomService.setRoom(roomId, setRoomDTO);
        return Result.success(null);
    }

    /**
     * 创建考场
     * @param roomId
     * @return
     */
    @PostMapping("/create")
    public Result<Object> createRoom(@RequestParam(name = "room_id") int roomId,
                                     @RequestBody SetRoomDTO setRoomDTO) {
        roomService.createRoom(roomId,setRoomDTO);
        return Result.success(null);
    }
}
