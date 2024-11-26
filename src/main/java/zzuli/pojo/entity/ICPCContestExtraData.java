package zzuli.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: ICPCContestExtraData
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ICPCContestExtraData  implements Serializable {
    private String banner;
    private int penalty;
    private boolean frozen;
    private int frozenTime;
}
