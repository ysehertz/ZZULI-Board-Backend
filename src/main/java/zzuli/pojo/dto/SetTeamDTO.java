package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: SetTeamDTO
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
public class SetTeamDTO implements Serializable {
    private String name;
    private String coach;
    private String school;
    private String collage;
    @JsonProperty("class")
    private String clazz;
    private boolean official;
}
