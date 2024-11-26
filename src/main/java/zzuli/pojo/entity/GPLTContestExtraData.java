package zzuli.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: GPLTContestExtraData
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
public class GPLTContestExtraData  implements Serializable {
    private int standard_1;
    private int standard_2;
}
