package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.common.constant.MessageConstant;
import zzuli.common.exception.NoSchoolException;
import zzuli.mapper.ClassMapper;
import zzuli.mapper.CollageMapper;
import zzuli.mapper.SchoolMapper;
import zzuli.pojo.dto.CreateSchoolDTO;
import zzuli.pojo.entity.School;
import zzuli.service.SchoolService;

import java.util.List;

/**
 * ClassName: ScholServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CollageMapper collageMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    /**
     * 创建学校
     * @param dto 创建学校的数据传输对象
     */
    @Override
    public void createSchool(CreateSchoolDTO dto) {
        schoolMapper.craeteSchool(dto);
    }

    /**
     * 根据学校id修改学校信息
     * @param schoolId
     * @param dto
     */
    @Override
    public void setSchool(int schoolId, CreateSchoolDTO dto) {
        // 判断学校是否存在
        if (schoolMapper.getSchoolById(schoolId) == null) {
            throw new NoSchoolException(MessageConstant.NO_SCHOOL);
        }
        School school = School.builder()
                .schoolId(schoolId)
                .name(dto.getName())
                .avatar(dto.getAvatar())
                .build();
        schoolMapper.setSchool(school);
    }

    @Override
    public void deleteSchool(int schoolId) {
        List<Integer> collageIds = collageMapper.getCollageIdsBySchoolId(schoolId);
        for (Integer collageId : collageIds) {
            classMapper.deleteClassByCollageId(collageId);
        }
        collageMapper.deleteCollageBySchoolId(schoolId);
        schoolMapper.deleteSchool(schoolId);
    }

    @Override
    public List<School> list() {
        return schoolMapper.list();
    }
}
