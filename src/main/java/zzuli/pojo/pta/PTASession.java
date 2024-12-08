package zzuli.pojo.pta;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: PTASession
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/14
 */
@Builder
@Data
public class PTASession implements Serializable {
    private String Jsession;
    private String PTASession;
}
