package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: SignalDTO
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
public class SingleDTO implements Serializable {
    private String reg_code;
    private String id;
    private String name;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
}
