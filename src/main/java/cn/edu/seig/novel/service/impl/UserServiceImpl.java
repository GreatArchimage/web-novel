package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dao.mapper.UserInfoMapper;
import cn.edu.seig.novel.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoMapper userInfoMapper;

    @Override
    public Result listUsers() {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        return Result.success(userInfoMapper.selectList(userInfoQueryWrapper));
    }

    @Override
    public Result getUserInfo(Long userId) {
        return null;
    }
}
