package zzuli.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: TeamListVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Data
@Builder
public class TeamListVO implements Serializable {
    private int id;
    private String name;
    private String coach;
    private String school;
    private String college;
    @JsonProperty("class")
    private String clazz;
    private boolean official;
}
