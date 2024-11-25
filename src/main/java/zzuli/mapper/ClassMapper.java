package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.CreateClassDTO;

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
}
