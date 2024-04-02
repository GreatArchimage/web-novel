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
        Long userId = authSSO(jwtUtils, userInfoMapper, token);
        if (EXCLUDE_URI.contains(requestUri)) {
            return true;
        }
        // 作家权限认证
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
            .eq("user_id", userId)
            .last("LIMIT 1");
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);

        if (Objects.isNull(authorInfo)) {
            // 作家账号不存在
            return false;
        }
        if(authorInfo.getStatus() != 0){
            // 作家账号被禁用
            return false;
        }
        // 设置作家ID到当前线程
        UserHolder.setAuthorId(authorInfo.getId());
        return true;
    }
    
}