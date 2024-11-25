package zzuli.mapper;

import org.apache.ibatis.annotations.Mapper;
import zzuli.pojo.dto.CreateCollageDTO;

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
}
