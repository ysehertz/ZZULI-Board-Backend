package zzuli.service;

import zzuli.pojo.dto.CreateClassDTO;
import zzuli.pojo.dto.SetClassDTO;

/**
 * ClassName: ClassService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
public interface ClassService {
    void createClass(CreateClassDTO dto);

    void setClass(int classId, SetClassDTO dto);

    void deleteClass(int classId);
}
