package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.mapper.MemberMapper;
import zzuli.mapper.RoomMapper;
import zzuli.pojo.dto.SetRoomDTO;
import zzuli.pojo.entity.Room;
import zzuli.pojo.vo.RoomVO;
import zzuli.service.MemberService;
import zzuli.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: RoomServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<RoomVO> list(String contestId) {
        List<Room> rooms = roomMapper.list(contestId);
        if (rooms != null) {
            return rooms.stream().map(room -> RoomVO.builder()
                    .id(room.getRoomId())
                    .name(room.getRoomName())
                    .size(room.getRoomSize())
                    .build()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 删除考场及考场内的学生的考场信息
     * @param roomId
     */
    @Override
    public void deleteRoom(String roomId) {
        //删除考场内学生的考场信息
        memberMapper.upRoomIdToNull(roomId);
       // 删除考场
        roomMapper.deleteRoom(roomId);
    }

    /**
     * 设置考场信息
     * @param roomId
     * @param setRoomDTO
     */
    @Override
    public void setRoom(int roomId, SetRoomDTO setRoomDTO) {
        Room room = Room.builder()
                .roomId(roomId)
                .roomName(setRoomDTO.getName())
                .roomSize(setRoomDTO.getSize())
                .build();
        roomMapper.setRoom(room);
    }

    @Override
    public int createRoom(String contestID,SetRoomDTO setRoomDTO) {
        Room room = Room.builder()
                .contestId(contestID)
                .roomSize(setRoomDTO.getSize())
                .roomName(setRoomDTO.getName())
                .build();
        roomMapper.createRoom(room);
        return room.getRoomId();
    }

    @Override
    public void deleteRoomByContestID(String contestId) {
        roomMapper.deleteRoomByContestID(contestId);
    }
}
