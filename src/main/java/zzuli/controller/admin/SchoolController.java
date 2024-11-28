package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.common.Context.BaseContext;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateSchoolDTO;
import zzuli.service.SchoolService;

/**
 * ClassName: SchoolController
 * Package: zzuli.controller.admin
 * Description:学校目录的相关管理
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */

/**
 * 学校管理
 */
@RestController("adminSchoolController")
@RequestMapping("/api/admin/school")
@Slf4j
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/create")
    public Result<Integer> createSchool(@RequestBody CreateSchoolDTO dto) {
        schoolService.createSchool(dto);
        log.info("创建学校;管理员：{}，学校名：{}",BaseContext.getCurrentId(),dto.getName());
        return Result.success(null);
    }

    /**
     * 修改学校信息
     * @param schoolId
     * @param dto
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setSchool(@RequestParam("school_id") int schoolId,
                                    @RequestBody CreateSchoolDTO dto) {
        schoolService.setSchool(schoolId ,dto);
        log.info("修改学校信息;管理员：{}，学校ID：{}",BaseContext.getCurrentId(),schoolId);
        return Result.success(null);
    }

    /**
     * 删除学校
     * @param schoolId
     * @return
     */
    @PostMapping("/delete")
    public Result<Integer> deleteSchool(@RequestParam("school_id") int schoolId) {
        schoolService.deleteSchool(schoolId);
        log.info("删除学校;管理员：{}，学校ID：{}",BaseContext.getCurrentId(),schoolId);
        return Result.success(null);
    }

}
