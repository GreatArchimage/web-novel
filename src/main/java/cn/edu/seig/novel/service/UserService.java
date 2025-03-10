package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dto.UserRegisterReqDto;

public interface UserService {

   Result listUsers();

    /**
     * 用户信息查询
     */
    Result getUserInfo(Long userId);

    Result register(UserRegisterReqDto dto);

    Result login(UserInfo userInfo);

    Result updateUserInfo(UserInfo userInfo);

}
