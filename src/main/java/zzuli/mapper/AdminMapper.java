package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.entity.Admin;

/**
 * ClassName: AdminMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
@Mapper
public interface AdminMapper {
    Admin selectAdminByUname(String username);
}
