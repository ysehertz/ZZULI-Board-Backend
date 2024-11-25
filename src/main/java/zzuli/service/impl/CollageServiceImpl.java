package zzuli.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import zzuli.common.constant.MessageConstant;
import zzuli.common.exception.BaseException;
import zzuli.common.exception.NoSchoolException;
import zzuli.mapper.ClassMapper;
import zzuli.mapper.CollageMapper;
import zzuli.mapper.SchoolMapper;
import zzuli.pojo.dto.CreateCollageDTO;
import zzuli.service.CollageService;

import java.util.prefs.BackingStoreException;

/**
 * ClassName: CollageServiceImpl
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Service
@Slf4j
public class CollageServiceImpl implements CollageService {
    @Autowired
    private CollageMapper collageMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public void createCollage(CreateCollageDTO dto) {
        log.info("create collage: {}", dto.getName());
        if(schoolMapper.IsExistSchool(dto.getSchool_id()) == 0){
            throw new NoSchoolException(MessageConstant.NO_SCHOOL);
        }
        collageMapper.createCollage(dto);
    }

    @Override
    public void setCollage(int collageId, String name) {
        String oldName =  collageMapper.getCollage(collageId);
        if(null == oldName){
            throw new BaseException("学院不存在");
        }
        log.info("set collage: {}", oldName + "---->" +name);
        collageMapper.setCollage(collageId,name);
    }

    @Override
    public void deleteCollage(int collageId) {
        classMapper.deleteClassByCollageId(collageId);
    }
}
