package zzuli.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: StudentUser
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
public class StudentUser implements Serializable {
    private String studentNumber;
    private String name;
    private String id;
}
