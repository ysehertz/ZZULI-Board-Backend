package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

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
@Builder
public class SetMemberDTO implements Serializable {
    private String id;
    private String name;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
    private int room_id;
    private boolean official;
}
