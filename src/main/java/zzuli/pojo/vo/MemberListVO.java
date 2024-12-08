package zzuli.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: MemberListVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
public class MemberListVO implements Serializable {
    private String qq;
    private String phone;
    private int team_id;
    private int room_id;
    private String member_id;
    private String name;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
    private boolean official;
}
