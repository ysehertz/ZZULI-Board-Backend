package zzuli.pojo.entity;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Clazz
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
public class Clazz  {
    private int classId;
    private int collageId;
    private String className;
    private int classCount;
}
