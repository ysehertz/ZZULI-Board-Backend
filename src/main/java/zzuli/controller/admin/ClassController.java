package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateClassDTO;
import zzuli.service.ClassService;

/**
 * ClassName: ClassController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */

/**
 * 班级管理
 */
@RestController("adminClassController")
@RequestMapping("/api/admin/class")
@Slf4j
public class ClassController {
    @Autowired
    private ClassService classService;

    /**
     * 创建班级
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public Result<Integer> createClass(@RequestBody CreateClassDTO dto) {
        classService.createClass(dto);
        return Result.success(null);
    }

}
