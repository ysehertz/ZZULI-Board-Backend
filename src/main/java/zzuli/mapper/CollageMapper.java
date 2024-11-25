package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.CreateCollageDTO;

import java.util.List;

/**
 * ClassName: CollageMapper
 * Package: zzuli.mapper
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
@Mapper
public interface CollageMapper {
    void createCollage(CreateCollageDTO dto);

    String getCollage(int collageId);

    void setCollage(int collageId, String name);

    String getCollageName(int collageId);

    void deleteCollageByClassId(int classId);

    List<Integer> getCollageIdsBySchoolId(int schoolId);

    void deleteCollageBySchoolId(int schoolId);

}
