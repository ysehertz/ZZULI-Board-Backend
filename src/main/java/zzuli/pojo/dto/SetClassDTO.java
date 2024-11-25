package zzuli.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: SetClassDTO
 * Package: zzuli.pojo.dto
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetClassDTO implements Serializable {
    private String name;
    private int count;
}
