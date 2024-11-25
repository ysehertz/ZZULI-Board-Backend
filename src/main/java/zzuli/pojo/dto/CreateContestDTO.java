package zzuli.pojo.dto;

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
 * Description:
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
    private String PTASession;
    private String Jsession;
    private String title;
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
    private JsonNode school_list;
    private String S_balloon_color;
    private String S_school_list;
}
