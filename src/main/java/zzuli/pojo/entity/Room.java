package zzuli.pojo.entity;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Room
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
public class Room {
    private int roomId;
    private String roomName;
    private String contestId;
    private int roomSize;
}
