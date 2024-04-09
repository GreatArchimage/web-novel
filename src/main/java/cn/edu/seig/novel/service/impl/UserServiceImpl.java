package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.cache.VerifyCodeCacheManager;
import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dao.mapper.UserInfoMapper;
import cn.edu.seig.novel.dto.UserRegisterReqDto;
import cn.edu.seig.novel.dto.resp.UserRespDto;
import cn.edu.seig.novel.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoMapper userInfoMapper;

    private final JwtUtils jwtUtils;

    private final VerifyCodeCacheManager verifyCodeCacheManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Result listUsers() {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        return Result.success(userInfoMapper.selectList(userInfoQueryWrapper));
    }

    @Override
    public Result getUserInfo(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return Result.success(userInfo);
    }

    @Override
    public Result updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoMapper.updateById(userInfo);
        return Result.success();
    }

    @Override
    public Result register(UserRegisterReqDto dto) {
        // 从redis检验图形验证码是否正确
        if(!verifyCodeCacheManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())){
            return Result.fail("验证码错误");
        }

        //校验用户名是否已存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", dto.getUsername())
                .last("limit 1");
        if(userInfoMapper.selectCount(queryWrapper) > 0){
            return Result.fail("用户名已存在");
        }

        // 保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(dto.getUsername());
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        userInfo.setNickName(dto.getUsername());
        userInfo.setStatus(0);
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoMapper.insert(userInfo);

        //删除验证码
        verifyCodeCacheManager.removeImgVerifyCode(dto.getSessionId());

        //生成JWT返回
        return Result.success(UserRespDto.builder()
                .token(jwtUtils.generateToken(userInfo.getId(), "front"))
                .uid(userInfo.getId())
                .build());
    }

    @Override
    public Result login(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfo.getUsername())
                .last("limit 1");
        UserInfo user = userInfoMapper.selectOne(queryWrapper);
        if(user == null){
            return Result.fail("用户不存在");
        }
//        if(!user.getPassword().equals(userInfo.getPassword())){
//            return Result.fail("密码错误");
//        }
        if(!passwordEncoder.matches(userInfo.getPassword(), user.getPassword())){
            return Result.fail("密码错误");
        }
        if(user.getStatus() == 1){
            return Result.fail("用户已被禁用");
        }
        return Result.success(UserRespDto.builder()
                .token(jwtUtils.generateToken(user.getId(), "front"))
                .uid(user.getId())
                .nickName(user.getNickName())
                .build());

    }


}
