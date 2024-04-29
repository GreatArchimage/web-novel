package cn.edu.seig.novel.auth;

import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FrontAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    @Override
    public boolean auth(String token, String requestUri) {
        Long userId = authSSO(jwtUtils, userInfoMapper, token);
        return userId != null;
    }

}