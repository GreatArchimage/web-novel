package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;

public interface UserService {

   Result listUsers();

    /**
     * 用户信息查询
     * @param userId 用户ID
     * @return 用户信息
     */
    Result getUserInfo(Long userId);
}
