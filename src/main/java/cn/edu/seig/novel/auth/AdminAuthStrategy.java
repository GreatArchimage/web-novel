package cn.edu.seig.novel.auth;

import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.dao.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 平台后台管理系统 认证授权策略
 */
@Component
@RequiredArgsConstructor
public class AdminAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final SysUserMapper sysUserMapper;

    @Override
    public boolean auth(String token, String requestUri)  {
        if (!StringUtils.hasText(token)) {
            // token 为空
            return false;
        }
        Long sysUserId = jwtUtils.parseToken(token, "admin");
        if (sysUserId == null) {
            // token 解析失败
            return false;
        }
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (sysUser == null) {
            // 用户不存在
            return false;
        }
        // TODO 可能需要设置用户ID到当前线程
        return true;
    }
    
}