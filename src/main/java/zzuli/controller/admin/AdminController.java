package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.constant.JwtClaimsConstant;
import zzuli.common.properties.JwtProperties;
import zzuli.common.result.Result;
import zzuli.pojo.dto.AdminLoginDTO;
import zzuli.pojo.entity.Admin;
import zzuli.pojo.vo.AdminLoginVO;
import zzuli.service.AdminService;
import zzuli.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminController
 * Package: zzuli.controller.admin
 * Description: 管理员控制器
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/9
 */

/**
 * 管理员
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param adminDTO
     * @return
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminDTO) {
        log.info("管理员登录：{}", adminDTO);
        Admin admin = adminService.login(adminDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getUname());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .BoardSession(token)
                .build();
        return Result.success(adminLoginVO);
    }

    @GetMapping("/admin/test")
    public Result<String> test() {

        return Result.success(jwtProperties.getAdminTokenName());
    }

}
