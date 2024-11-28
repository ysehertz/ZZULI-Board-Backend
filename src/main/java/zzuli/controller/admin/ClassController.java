package zzuli.controller.admin;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzuli.common.Context.BaseContext;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateClassDTO;
import zzuli.pojo.dto.SetClassDTO;
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
    public Result<Integer> createClass(@RequestParam("college_id") int collageId,
            @RequestBody CreateClassDTO dto) {
        dto.setCollage_id(collageId);
        classService.createClass(dto);
        log.info("创建班级;管理员：{}，班级名：{}", BaseContext.getCurrentId(),dto.getName());
        return Result.success(null);
    }

    /**
     * 修改班级信息
     * @param classId
     * @param dto
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setClass(@RequestParam(name = "class_id") int classId,
            @RequestBody SetClassDTO dto) {
        classService.setClass(classId,dto);
        log.info("修改班级信息;管理员：{}，班级ID：{}", BaseContext.getCurrentId(),classId);
        return Result.success(null);
    }

    /**
     * 删除班级
     * @param classId
     * @return
     */
    @PostMapping("/delete")
    public Result<Integer> deleteClass(@RequestParam(name = "class_id") int classId) {
        classService.deleteClass(classId);
        log.info("删除班级;管理员：{}，班级ID：{}", BaseContext.getCurrentId(),classId);
        return Result.success(null);
    }

}
