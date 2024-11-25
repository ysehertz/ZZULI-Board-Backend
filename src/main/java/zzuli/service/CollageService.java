package zzuli.service;

import zzuli.pojo.dto.CreateCollageDTO;

/**
 * ClassName: CollageService
 * Package: zzuli.service.impl
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
public interface CollageService {
    void createCollage(CreateCollageDTO dto);

    void setCollage(int collageId, String name);
}
