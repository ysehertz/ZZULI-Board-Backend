package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: SetMemberDTO
 * Package: zzuli.pojo.dto
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetMemberDTO implements Serializable {
    private String qq;
    private String phone;
    private String contest_id;
    private String member_id;
    private String id;
    private String name;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
    private int room_id;
    private boolean official;
}
