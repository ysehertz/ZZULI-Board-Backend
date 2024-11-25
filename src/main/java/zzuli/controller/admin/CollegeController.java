package zzuli.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/api/admin/collage")
@Slf4j
public class CollegeController {
    @Autowired
    private CollageService collegeService;


    /**
     * 创建学院
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public Result<Integer> createCollage(@RequestBody CreateCollageDTO dto) {
        collegeService.createCollage(dto);
        return Result.success(null);
    }

    @PostMapping("/set")
    public Result<Integer> setCollage(@RequestParam(name = "collage_id") int collageId,
                            @RequestBody String name) {
        collegeService.setCollage(collageId,name);
        return Result.success(null);
    }
}
