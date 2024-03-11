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

        // 废弃方案
//        IPage<BookComment> page = new Page<>();
//        page.setCurrent(params.getPageNum());
//        page.setSize(params.getPageSize());
//        QueryWrapper<BookComment> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("create_time");
//        IPage<BookComment> commentIPage = bookCommentMapper.selectPage(page, queryWrapper);
//        List<BookComment> commentList = commentIPage.getRecords();
//        // 将所有评论的user_id和book_id取出
//        List<Long> userIds = commentList.stream().map(BookComment::getUserId).toList();
//        List<Long> bookIds = commentList.stream().map(BookComment::getBookId).toList();
//        // 根据查询结果的user_id和book_id查询用户信息和小说信息
//        List<UserInfo> userInfoList = userInfoMapper.selectBatchIds(userIds);
//        List<BookInfo> bookInfoList = bookInfoMapper.selectBatchIds(bookIds);
//        // 将查询结果封装成CommentListItemRespDto


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

//    @Override
//    public Result listBookRecommend() {
//        // 多表查询book_recommend和book_info
//        QueryWrapper<BookRecommend> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("create_time");
//        List<BookRecommend> bookRecommends = bookRecommendMapper.selectList(queryWrapper);
//        List<Long> bookIds = bookRecommends.stream().map(BookRecommend::getBookId).toList();
//        List<BookInfo> bookInfos = bookInfoMapper.selectBatchIds(bookIds);
//
//    }

    @Override
    public Result deleteBookRecommend(Long id) {
        int i = bookRecommendMapper.deleteById(id);
        if (i == 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }
}
