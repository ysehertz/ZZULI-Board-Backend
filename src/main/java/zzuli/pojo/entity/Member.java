package zzuli.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: Member
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    private int uuid;
    private String contestId;
    private int teamId;
    private String memberId;
    private String memberName;
    private String memberSchool;
    private String memberCollege;
    private String memberClass;
    private int roomId;
    private boolean official; // 是否是正式成员
}
