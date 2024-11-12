package zzuli.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: RecordVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Data
@Builder
public class RecordVO implements Serializable {
    private String record_id;
    private String member_id;
    private String problem_id;
    private String status;
    private int score;
    private String language;
    private java.sql.Timestamp submit_time;
    private boolean balloon;
}
