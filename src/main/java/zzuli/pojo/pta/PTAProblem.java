package zzuli.pojo.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: PTAProblem
 * Package: zzuli.pojo.pta
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PTAProblem implements Serializable {
    private String id;
    @JsonProperty("title")
    private String name;
    private String score;
}