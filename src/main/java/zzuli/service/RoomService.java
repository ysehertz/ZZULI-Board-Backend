package zzuli.service;

import zzuli.pojo.dto.SetRoomDTO;
import zzuli.pojo.vo.RoomVO;

import java.util.List;

/**
 * ClassName: RoomService
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
public interface RoomService {
    List<RoomVO> list(String contestId);

    void deleteRoom(String roomId);

    void setRoom(int roomId, SetRoomDTO setRoomDTO);

    int createRoom(String contestID, SetRoomDTO setRoomDTO);

    void deleteRoomByContestID(String contestId);
}
