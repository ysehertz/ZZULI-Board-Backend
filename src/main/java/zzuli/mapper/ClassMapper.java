package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.CreateClassDTO;
import zzuli.pojo.entity.Clazz;

import java.util.List;

/**
 * ClassName: ClassMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Mapper
public interface ClassMapper {

    void createClass(CreateClassDTO dto);

    void setClass(Clazz clazz);

    void deleteClass(int classId);

    void deleteClassByCollageId(int collageId);

}
