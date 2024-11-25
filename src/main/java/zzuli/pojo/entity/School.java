package zzuli.pojo.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: School
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
public class School implements Serializable {
    private int SchoolId;
    private String name;
    private String avatar;
}
