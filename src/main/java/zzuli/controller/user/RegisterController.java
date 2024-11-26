package zzuli.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zzuli.common.result.Result;
import zzuli.pojo.entity.Clazz;
import zzuli.pojo.entity.College;
import zzuli.pojo.entity.School;
import zzuli.service.ClassService;
import zzuli.service.CollageService;
import zzuli.service.SchoolService;

import java.util.List;

/**
 * ClassName: RegisterController
 * Package: zzuli.controller.user
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/26
 */
/**
 * 学生注册相关信息
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class RegisterController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ClassService classService;
    @Autowired
    private CollageService collageService;

    /**
     * 获取学校列表
     * @return
     */
    @GetMapping("/school")
    public Result<List<School>> getSchool() {
        return Result.success(schoolService.list());
    }

    /**
     * 获取学院列表
     * @param schoolId
     * @return
     */
    @GetMapping("/college")
    public Result<List<College>> getCollege(@RequestParam(name = "school_id") int schoolId) {
        return Result.success(collageService.list(schoolId));
    }

    /**
     * 获取班级列表
     * @param collegeId
     * @return
     */
    @GetMapping("/class")
    public Result<List<Clazz>> getClazz(@RequestParam(name = "college_id") int collegeId) {
        return Result.success(classService.list(collegeId));
    }
}
