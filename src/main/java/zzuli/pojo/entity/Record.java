package zzuli.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Record
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/13
 */

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Record implements Serializable {
    private String contestId;
    private String memberId;
    private String userId;
    private String id;
    private String problemType;
    private String problemSetProblemId;
    private java.sql.Timestamp submitAt;
    private String status;
    private String score;
    private String compiler;
    private String time;
    private String memory;
    private String previewSubmission;
    private java.sql.Timestamp judgeAt;
}
