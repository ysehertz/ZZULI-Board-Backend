package zzuli.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: PTAMembers
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/13
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PTAMembers implements Serializable {
    private StudentUser studentUser;
    private String examId;
}
