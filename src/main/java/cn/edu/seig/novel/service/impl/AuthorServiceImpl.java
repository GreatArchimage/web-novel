package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.AuthorInfo;
import cn.edu.seig.novel.dao.mapper.AuthorInfoMapper;
import cn.edu.seig.novel.service.AuthorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoMapper authorInfoMapper;

    @Override
    public Result getAuthorStatus(Long userId) {
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).last("LIMIT 1");
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);
        return Objects.isNull(authorInfo) ? Result.success(null) : Result.success(authorInfo.getStatus());
    }

    @Override
    public Result registerAuthor(AuthorInfo authorInfo) {
        // 校验该用户是否已注册为作家
        AuthorInfo author = authorInfoMapper.selectById(authorInfo.getUserId());
        if (Objects.nonNull(author)) {
            // 该用户已经是作家，直接返回
            return Result.success();
        }
        // 保存作家注册信息
        authorInfo.setCreateTime(LocalDateTime.now());
        authorInfo.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.insert(authorInfo);
        return Result.success();
    }

    @Override
    public Result getAuthorInfo(Long userId) {
        QueryWrapper<AuthorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).last("LIMIT 1");
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);
        return Objects.isNull(authorInfo) ? Result.success(null) : Result.success(authorInfo);
    }

    @Override
    public Result updateAuthorInfo(AuthorInfo authorInfo) {
        authorInfo.setUpdateTime(LocalDateTime.now());
        // 根据userId更新作家信息
        authorInfoMapper.update(authorInfo, new QueryWrapper<AuthorInfo>().eq("user_id", authorInfo.getUserId()));
        return Result.success();
    }
}
