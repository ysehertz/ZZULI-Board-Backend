package zzuli.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private String contestId;
    @JsonProperty("PTASession")
    private String PTASession;
    @JsonProperty("jesession")
    private String Jesession;
    @JsonProperty("type")
    private String contestType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("start_time")
    private java.sql.Timestamp startTime;
    @JsonProperty("end_time")
    private java.sql.Timestamp endTime;
    @JsonProperty("reg_type")
    private String regType;
    @JsonProperty("reg_start_time")
    private java.sql.Timestamp regStartTime;
    @JsonProperty("reg_end_time")
    private java.sql.Timestamp regEndTime;
    @JsonProperty("reg_off_code")
    private String regOffCode;
    @JsonProperty("reg_unoff_code")
    private String regUnoffCode;
    private String extra;
    @JsonProperty("balloon_color")
    private String balloonColor;
    @JsonProperty("problem_list")
    private String problemList;
}
