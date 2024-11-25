package zzuli.pojo.entity;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: College
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
public class College {
    private int collegeId;
    private int schoolId;
    private String collegeName;
}
