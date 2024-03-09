package cn.edu.seig.novel.auth;

import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.entity.AuthorInfo;
import cn.edu.seig.novel.dao.mapper.AuthorInfoMapper;
import cn.edu.seig.novel.dao.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 作家后台管理系统 认证授权策略
 */
@Component
@RequiredArgsConstructor
public class AuthorAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    private final AuthorInfoMapper authorInfoMapper;

    /**
     * 不需要进行作家权限认证的 URI
     */
    private static final List<String> EXCLUDE_URI = List.of(
        "/api/author/register",
        "/api/author/status"
    );

    @Override
    public boolean auth(String token, String requestUri) {
        // 统一账号认证
        Long userId = authSSO(jwtUtils, userInfoMapper, token);
        if (EXCLUDE_URI.contains(requestUri)) {
            // 该请求不需要进行作家权限认证
            return true;
        }
        // 作家权限认证
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
            .eq("user_id", userId)
            .last("LIMIT 1");
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);

        if (Objects.isNull(authorInfo)) {
            // 作家账号不存在，无权访问作家专区
            return false;
        }

        // 设置作家ID到当前线程
        UserHolder.setAuthorId(authorInfo.getId());
        return true;
    }
    
}