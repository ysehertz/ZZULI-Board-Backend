package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.common.exception.BaseException;
import zzuli.mapper.ClassMapper;
import zzuli.mapper.CollageMapper;
import zzuli.pojo.dto.CreateClassDTO;
import zzuli.pojo.dto.SetClassDTO;
import zzuli.pojo.entity.Clazz;
import zzuli.service.ClassService;

import java.util.List;

/**
 * ClassName: ClassServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Service
@Slf4j
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CollageMapper collageMapper;
    @Override
    public void createClass(CreateClassDTO dto) {
        String collageName = collageMapper.getCollageName(dto.getCollage_id());
        if(collageName == null) {
            throw new BaseException("学院不存在");
        }
        log.info("为{}创建班级{}", collageName, dto.getName());
        classMapper.createClass(dto);
    }

    @Override
    public void setClass( int classId, SetClassDTO dto) {
        Clazz clazz = Clazz.builder()
                .classCount(dto.getCount())
                .className(dto.getName())
                .classId(classId)
                .build();
        log.info("设置班级{}的信息", classId);
        classMapper.setClass(clazz);
    }

    @Override
    public void deleteClass(int classId) {
        classMapper.deleteClass(classId);
        collageMapper.deleteCollageByClassId(classId);
    }

    @Override
    public List<Clazz> list(int collegeId) {
        return classMapper.list(collegeId);
    }
}
