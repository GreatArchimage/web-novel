package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.JwtUtils;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.common.utils.PageResp;
import cn.edu.seig.novel.dao.entity.*;
import cn.edu.seig.novel.dao.mapper.*;
import cn.edu.seig.novel.dto.resp.CommentListItemRespDto;
import cn.edu.seig.novel.dto.resp.SysUserRespDto;
import cn.edu.seig.novel.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final JwtUtils jwtUtils;

    private final SysUserMapper sysUserMapper;
    private final BookCommentMapper bookCommentMapper;

    private final UserInfoMapper userInfoMapper;
    private final BookInfoMapper bookInfoMapper;

    private final BookRecommendMapper bookRecommendMapper;

    private final BookCategoryMapper bookCategoryMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Result login(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", sysUser.getUsername());
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user == null) {
            return Result.fail("账号不存在");
        }
        if (!user.getPassword().equals(sysUser.getPassword())) {
            return Result.fail("密码错误");
        }
        return Result.success(SysUserRespDto.builder()
                .uid(user.getId())
                .username(user.getUsername())
                .token(jwtUtils.generateToken(user.getId(), "admin"))
                .build());
    }

    @Override
    public Result listComments(PageReqParams params) {
        IPage<CommentListItemRespDto> page = new Page<>();
        page.setCurrent(params.getPageNum());
        page.setSize(params.getPageSize());
        QueryWrapper<CommentListItemRespDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<CommentListItemRespDto> commentList = bookCommentMapper.listComments(page, queryWrapper);
        return Result.success(new PageResp<>(
                page.getCurrent(),page.getSize(), page.getTotal(), commentList));

    }

    @Override
    public Result deleteComment(Long id) {
        int i = bookCommentMapper.deleteById(id);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result addBookRecommend(Long bookId, Integer recommendType) {
        BookRecommend bookRecommend = bookRecommendMapper.selectOne(new QueryWrapper<BookRecommend>()
                .eq("book_id", bookId)
                .eq("type", recommendType));
        if (bookRecommend != null) {
            return Result.fail("已存在");
        }
        BookRecommend newBookRecommend = new BookRecommend();
        newBookRecommend.setBookId(bookId);
        newBookRecommend.setType(recommendType);
        newBookRecommend.setCreateTime(LocalDateTime.now());
        newBookRecommend.setUpdateTime(LocalDateTime.now());
        int i = bookRecommendMapper.insert(newBookRecommend);
        if (i == 0) {
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public Result deleteBookRecommend(Long id) {
        int i = bookRecommendMapper.deleteById(id);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result saveCategory(BookCategory bookCategory) {
        bookCategory.setCreateTime(LocalDateTime.now());
        bookCategory.setUpdateTime(LocalDateTime.now());
        int i = bookCategoryMapper.insert(bookCategory);
        if (i == 0) {
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public Result deleteCategory(Long id) {
        int i = bookCategoryMapper.deleteById(id);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result listSysUsers() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return Result.success(sysUsers);
    }

    @Override
    public Result addSysUser(SysUser sysUser) {
        // 检查用户名是否已存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", sysUser.getUsername());
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user != null) {
            return Result.fail("用户名已存在");
        }
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        int i = sysUserMapper.insert(sysUser);
        if (i == 0) {
            return Result.fail("添加失败");
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(1L);
        sysUserRoleMapper.insert(sysUserRole);
        return Result.success("添加成功");
    }

    @Override
    public Result deleteSysUser(Long id) {
        // 检查是否为超级管理员
        SysUserRole sysUserRole = sysUserRoleMapper.selectOne(new QueryWrapper<SysUserRole>().eq("user_id", id));
        if (sysUserRole == null || sysUserRole.getRoleId() == 0) {
            return Result.fail("无法删除");
        }
        int i = sysUserMapper.deleteById(id);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", id));
        return Result.success("删除成功");
    }
}
