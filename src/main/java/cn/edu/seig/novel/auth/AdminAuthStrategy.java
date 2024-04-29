package cn.edu.seig.novel.auth;

import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.dao.entity.SysUserRole;
import cn.edu.seig.novel.dao.mapper.SysUserMapper;
import cn.edu.seig.novel.dao.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

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
        // 检查用户是否有权限访问该接口
//        String substring = requestUri.substring("/api/admin".length() + 1);
//        String moduleName = substring.substring(0, substring.indexOf("/"));
        if(requestUri.contains("sysUser")){
            if(requestUri.contains("list")){
                return true;
            }
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", sysUserId);
            SysUserRole sysUserRole = sysUserRoleMapper.selectOne(queryWrapper);
            if(sysUserRole == null || sysUserRole.getRoleId() != 0){
                return false;
            }
        }

        // TODO 可能需要设置用户ID到当前线程
        return true;
    }
    
}