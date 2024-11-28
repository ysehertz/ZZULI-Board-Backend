package zzuli.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * ClassName: ContestListVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestListVO implements Serializable {
    private String id;
    private String type;
    private String title;
    private Long start_time;
    private Long end_time;
    private int reg_type;
    private Long reg_start_time;
    private Long reg_end_time;
}
