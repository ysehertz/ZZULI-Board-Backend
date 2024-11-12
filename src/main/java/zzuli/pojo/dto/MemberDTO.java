package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Member
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Data
@Builder
public class MemberDTO implements Serializable {
    private String id;
    private String name;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
}
