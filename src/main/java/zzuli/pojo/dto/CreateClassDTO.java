package zzuli.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: CreateClassDTO
 * Package: zzuli.pojo.dto
 * Description: 创建班级DTO
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClassDTO implements Serializable {
    private int collage_id;
    private String name;
    private int count;
}
