package zzuli.service;

import zzuli.pojo.vo.MemberListVO;

import java.util.List;

/**
 * ClassName: MemberService
 * Package: zzuli.service
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2024/11/10
 */
public interface MemberService {

    List<MemberListVO> list();
}
