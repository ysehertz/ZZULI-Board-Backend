package zzuli.service;

import zzuli.pojo.dto.CreateSchoolDTO;

/**
 * ClassName: SchoolService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/25
 */
public interface SchoolService {
    void createSchool(CreateSchoolDTO dto);

    void setSchool(int schoolId, CreateSchoolDTO dto);

    void deleteSchool(int schoolId);
}
