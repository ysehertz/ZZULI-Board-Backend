package zzuli.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: ResgisterTeamVO
 * Package: zzuli.pojo.vo
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/12
 */
@Data
@Builder
public class RegisterTeamVO  implements Serializable {
    private int team_id;
}
