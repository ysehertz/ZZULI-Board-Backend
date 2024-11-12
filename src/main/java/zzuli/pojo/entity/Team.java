package zzuli.pojo.entity;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Team
 * Package: zzuli.pojo.entity
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/11
 */
@Data
@Builder
public class Team {
    private int teamId;
    private String contestId;
    private String teamName;
    private String teamCoach;
    private String teamSchool;
    private String teamCollege;
    private String teamClass;
    private boolean official;
}
