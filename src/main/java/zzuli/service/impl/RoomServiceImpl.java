package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.mapper.RoomMapper;
import zzuli.pojo.entity.Room;
import zzuli.pojo.vo.RoomVO;
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
}
