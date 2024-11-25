package zzuli.pojo.entity;

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
public class School implements Serializable {
    private int id;
    private String name;
    private String avatar;
}
