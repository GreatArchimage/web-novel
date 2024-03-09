package cn.edu.seig.novel.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 平台后台管理系统 认证授权策略
 */
@Component
@RequiredArgsConstructor
public class AdminAuthStrategy implements AuthStrategy {

    @Override
    public boolean auth(String token, String requestUri)  {
        // TODO 平台后台 token 校验
        return true;
    }
    
}