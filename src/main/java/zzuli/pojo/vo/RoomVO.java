package zzuli.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: RoomVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
public class RoomVO implements Serializable {
    private int id;
    private String name;
    private int size;
}
