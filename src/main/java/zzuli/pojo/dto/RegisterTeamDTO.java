package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: RegisterTeamVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTeamDTO implements Serializable {
    private String reg_code;
    private String name;
    private String coach;
    private String school;
    private String collage;
    @JsonProperty("class")
    private String clazz;
    private List<MemberDTO> members;
}
