package zzuli.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private java.sql.Timestamp start_time;
    private java.sql.Timestamp end_time;
    private String reg_type;
    private java.sql.Timestamp reg_start_time;
    private java.sql.Timestamp reg_end_time;
}
