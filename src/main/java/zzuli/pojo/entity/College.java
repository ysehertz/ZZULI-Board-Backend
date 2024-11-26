package zzuli.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ClassName: College
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
public class College {
    @JsonProperty("college_id")
    private int collegeId;
    @JsonIgnore
    private int schoolId;
    @JsonProperty("name")
    private String collegeName;
}
