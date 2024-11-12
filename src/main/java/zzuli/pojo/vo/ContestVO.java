package zzuli.pojo.vo;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import zzuli.pojo.entity.Balloon;
import zzuli.pojo.entity.Problem;
import zzuli.pojo.entity.School;

import java.io.Serializable;
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
    private String banner;
    private java.sql.Timestamp start_time;
    private java.sql.Timestamp end_time;
    private String reg_type;
    private java.sql.Timestamp reg_start_time;
    private java.sql.Timestamp reg_end_time;
    private int penalty;
    private boolean frozen;
    private java.sql.Timestamp frozen_time;
    private List<Balloon> balloon_color;
    private List<Problem> problem_list;
    private List<School> school_list;
}
