package zzuli.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Admin
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
@Data
public class Admin implements Serializable {
    private String uname; // 用户名
    private String password; // 密码
}
