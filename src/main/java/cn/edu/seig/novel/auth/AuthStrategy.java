package cn.edu.seig.novel.auth;

import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dao.mapper.UserInfoMapper;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 策略模式实现用户认证授权功能
 */
public interface AuthStrategy {

    /**
     * 用户认证授权
     *
     * @param token      登录 token
     * @param requestUri 请求的 URI
     */
    boolean auth(String token, String requestUri);

    /**
     * 前台多系统单点登录统一账号认证授权（门户系统、作家系统以及后面会扩展的漫画系统和视频系统等）
     *
     * @param jwtUtils             jwt 工具
     * @param userInfoCacheManager 用户缓存管理对象
     * @param token                token 登录 token
     * @return 用户ID
     */
    default Long authSSO(JwtUtils jwtUtils, UserInfoMapper userInfoMapper,
                         String token) {
        if (!StringUtils.hasText(token)) {
            // token 为空
            return null;
        }
        Long userId = jwtUtils.parseToken(token, "front");
        if (Objects.isNull(userId)) {
            // token 解析失败
            return null;
        }
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (Objects.isNull(userInfo)) {
            // 用户不存在
            return null;
        }
        // 设置 userId 到当前线程
        UserHolder.setUserId(userId);
        // 返回 userId
        return userId;
    }

}
