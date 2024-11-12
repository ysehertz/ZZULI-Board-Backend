package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.SetRoomDTO;
import zzuli.pojo.entity.Room;
import zzuli.pojo.vo.RoomVO;

import java.util.List;

/**
 * ClassName: RoomMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Mapper
public interface RoomMapper {
    List<Room> list(String contestId);

    void deleteRoom(String roomId);

    void setRoom(String roomId, SetRoomDTO DTO);

    void createRoom(Room room);

    void deleteRoomByContestID(String contestId);
}
