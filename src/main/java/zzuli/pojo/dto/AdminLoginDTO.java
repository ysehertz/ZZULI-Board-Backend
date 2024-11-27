package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: MemberDTO
 * Package: zzuli.pojo.DTO
 * Description:管理员登录时的数据传输对象
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO implements Serializable {
    @JsonProperty("username")
    private String uname; // 用户名
    private String password; // 密码
}
