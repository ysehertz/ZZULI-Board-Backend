package zzuli.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zzuli.pojo.entity.Balloon;

import java.io.Serializable;
import java.time.Instant;
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
public class ContestAdminVO implements Serializable {
    @JsonProperty("id")
    private String contestId;
    @JsonProperty("PTASession")
    private String PTASession;
    @JsonProperty("Jsession")
    private String Jsession;
    @JsonProperty("type")
    private String contestType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("end_time")
    private Long endTime;
    @JsonProperty("reg_type")
    private int regType;
    @JsonProperty("reg_start_time")
    private Long regStartTime;
    @JsonProperty("reg_end_time")
    private Long regEndTime;
    @JsonProperty("reg_off_code")
    private String regOffCode;
    @JsonProperty("reg_unoff_code")
    private String regUnoffCode;
    private JsonNode extra;
    @JsonProperty("balloon_color")
    private List<Balloon> balloonColor;
    @JsonProperty("problem_list")
    private List<ProblemVO> problemList;
}
