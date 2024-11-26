package zzuli.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Clazz
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
public class Clazz  {
    @JsonProperty("class_id")
    private int classId;
    @JsonIgnore
    private int collageId;
    @JsonProperty("name")
    private String className;
    @JsonProperty("count")
    private int classCount;
}
