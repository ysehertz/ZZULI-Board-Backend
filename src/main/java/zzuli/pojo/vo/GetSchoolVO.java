package zzuli.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: GetSchoolVO
 * Package: zzuli.pojo.vo
 * Description:获取学校列表时的返回方式
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Data
@Builder
public class GetSchoolVO implements Serializable {
    private int school_id;
    private String name;
    private String avatar;
}
