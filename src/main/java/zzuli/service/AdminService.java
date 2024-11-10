package zzuli.service;

import zzuli.pojo.dto.AdminLoginDTO;
import zzuli.pojo.entity.Admin;

/**
 * ClassName: AdminService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
public interface AdminService {
    /**
     * 管理员登录
     *
     * @param adminLoginDTO 管理员登录时的数据传输对象
     * @return token
     */
    Admin login(AdminLoginDTO adminLoginDTO);
}
