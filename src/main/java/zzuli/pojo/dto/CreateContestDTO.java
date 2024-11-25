package zzuli.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * ClassName: CreateContestDTO
 * Package: zzuli.pojo.dto
 * Description:创建比赛
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateContestDTO implements Serializable {
    private String id;
    private String type;
    @JsonProperty("PTASession")
    private String PTASession;
    @JsonProperty("Jsession")
    private String Jsession;
    private String title;
    private java.sql.Timestamp start_time;
    private java.sql.Timestamp end_time;
    private String reg_type;
    private java.sql.Timestamp reg_start_time;
    private java.sql.Timestamp reg_end_time;
    private String reg_off_code;
    private String reg_unoff_code;
    private String banner;
    private int penalty;
    private boolean frozen;
    private int frozen_time;
    private JsonNode balloon_color;
}
