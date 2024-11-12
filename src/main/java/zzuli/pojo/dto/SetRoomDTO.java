package zzuli.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: SetRoomDTO
 * Package: zzuli.pojo.dto
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */
@Data
@Builder
public class SetRoomDTO implements Serializable {
    private String name;
    private int size;
}
