package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import zzuli.common.Context.BaseContext;
import zzuli.common.result.Result;
import zzuli.pojo.dto.CreateCollageDTO;
import zzuli.service.CollageService;

/**
 * ClassName: CollegeController
 * Package: zzuli.controller.admin
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */

/**
 * 学院管理
 */
@RestController("adminCollageController")
@RequestMapping("/api/admin/college")
@Slf4j
public class CollegeController {
    @Autowired
    private CollageService collegeService;


    /**
     * 创建学院
     * @param
     * @return
     */
    @PostMapping("/create")
    public Result<Integer> createCollage(@RequestParam("school_id") int schoolId,
            @RequestBody CreateCollageDTO dto ) {
        dto.setSchool_id(schoolId);
        collegeService.createCollage(dto);
        log.info("创建学院;管理员：{}，学院名：{}",BaseContext.getCurrentId(),dto.getName());
        return Result.success(null);
    }

    /**
     * 修改学院名字
     * @param collageId
     * @param
     * @return
     */
    @PostMapping("/set")
    public Result<Integer> setCollage(@RequestParam(name = "college_id") int collageId,
                            @RequestBody CreateCollageDTO dto) {
        collegeService.setCollage(collageId,dto.getName());
        log.info("修改学院名字;管理员：{}，学院ID：{}，学院名：{}",BaseContext.getCurrentId(),collageId,dto.getName());
        return Result.success(null);
    }

    /**
     * 删除学院
     * @param collageId
     * @return
     */
    @PostMapping("/delete")
    public Result<Integer> deleteCollage(@RequestParam(name = "collage_id") int collageId) {
        collegeService.deleteCollage(collageId);
        log.info("删除学院;管理员：{}，学院ID：{}", BaseContext.getCurrentId(),collageId);
        return Result.success(null);
    }
}
