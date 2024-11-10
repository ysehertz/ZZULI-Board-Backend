package zzuli.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: AdminVO
 * Package: zzuli.pojo.vo
 * Description: 管理员登录时的数据传输对象
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
@Data
@Builder
public class AdminLoginVO implements Serializable {
    private String BoardSession; // token
}
