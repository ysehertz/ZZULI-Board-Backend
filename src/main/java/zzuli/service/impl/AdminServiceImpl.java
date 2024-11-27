package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import zzuli.common.constant.MessageConstant;
import zzuli.common.exception.PasswordErrorException;
import zzuli.mapper.AdminMapper;
import zzuli.pojo.dto.AdminLoginDTO;
import zzuli.pojo.entity.Admin;
import zzuli.pojo.vo.AdminLoginVO;
import zzuli.service.AdminService;

/**
 * ClassName: AdminServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(AdminLoginDTO adminLoginDTO) {
        log.info("管理员登录：{}", adminLoginDTO);
        String username = adminLoginDTO.getUname();
        String password = adminLoginDTO.getPassword();

        Admin admin = adminMapper.selectAdminByUname(username);

        if (admin == null ) {
            throw new PasswordErrorException("用户不存在");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(admin.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return admin;
    }
}
