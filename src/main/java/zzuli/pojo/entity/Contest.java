package zzuli.pojo.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: Contest
 * Package: zzuli.pojo.entity
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
public class Contest  implements Serializable {
    private String contestId;
    private String PTASession;
    private String jsession;
    private String contestType;
    private String title;
    private java.sql.Timestamp startTime;
    private java.sql.Timestamp endTime;
    private String regType;
    private java.sql.Timestamp regStartTime;
    private java.sql.Timestamp regEndTime;
    private String regOffCode;
    private String regUnoffConde;
    private String banner;
    private int penalty;
    private boolean frozen;
    private java.sql.Timestamp frozenTime;
    private String balloonColor;
    private String problemList;
    private String schoolList;
}
