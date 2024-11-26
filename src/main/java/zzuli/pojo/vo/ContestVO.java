package zzuli.pojo.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import zzuli.pojo.entity.Balloon;
import zzuli.pojo.entity.Problem;
import zzuli.pojo.entity.School;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * ClassName: ContestVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Data
@Builder
public class ContestVO implements Serializable {
    private String id;
    private String type;
    private String title;
    private Long start_time;
    private Long end_time;
    private String reg_type;
    private Long reg_start_time;
    private  Long reg_end_time;
    private JsonNode extra;
    private List<Balloon> balloon_color;
    private List<Problem> problem_list;
}
