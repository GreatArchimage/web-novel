package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.common.utils.PageResp;
import cn.edu.seig.novel.dao.entity.*;
import cn.edu.seig.novel.dao.mapper.*;
import cn.edu.seig.novel.dto.resp.BookChapterAboutRespDto;
import cn.edu.seig.novel.dto.resp.BookCommentRespDto;
import cn.edu.seig.novel.dto.resp.BookInfoRespDto;
import cn.edu.seig.novel.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    private final BookCategoryMapper bookCategoryMapper;

    private final BookCommentMapper bookCommentMapper;

    private final UserInfoMapper userInfoMapper;

    @Override
    public Result listVisitRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("visit_count")
                .last("limit 30");
        return Result.success(bookInfoMapper.selectList(bookInfoQueryWrapper));

    }

    @Override
    public Result listNewestRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("create_time").last("limit 30");
        return Result.success(bookInfoMapper.selectList(bookInfoQueryWrapper));
    }

    @Override
    public Result listUpdateRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("update_time").last("limit 30");
        return Result.success(bookInfoMapper.selectList(bookInfoQueryWrapper));
    }

    @Override
    public Result getBookById(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("book_id", bookId)
                .orderByAsc("chapter_num")
                .last("limit 1");
        BookChapter firstBookChapter = bookChapterMapper.selectOne(queryWrapper);
        // 组装响应对象
        BookInfoRespDto bookInfoRespDto = BookInfoRespDto.builder()
                .id(bookInfo.getId())
                .bookName(bookInfo.getBookName())
                .intro(bookInfo.getIntro())
                .bookStatus(bookInfo.getBookStatus())
                .authorId(bookInfo.getAuthorId())
                .authorName(bookInfo.getAuthorName())
                .categoryId(bookInfo.getCategoryId())
                .categoryName(bookInfo.getCategoryName())
                .commentCount(bookInfo.getCommentCount())
                .firstChapterId(firstBookChapter.getId())
                .lastChapterId(bookInfo.getLastChapterId())
                .picUrl(bookInfo.getPicUrl())
                .visitCount(bookInfo.getVisitCount())
                .wordCount(bookInfo.getWordCount())
                .build();
        return Result.success(bookInfoRespDto);
    }

    @Override
    public Result listBooks() {
        return Result.success(bookInfoMapper.selectList(null));
    }

    @Override
    public Result saveBook(BookInfo newBook) {
        // 检查小说名是否重复
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.eq("book_name", newBook.getBookName());
        if (bookInfoMapper.selectCount(bookInfoQueryWrapper) > 0) {
            return Result.fail("小说名重复");
        }
        //TODO 设置作家信息
        newBook.setAuthorId(1L);

        newBook.setCreateTime(LocalDateTime.now());
        newBook.setUpdateTime(LocalDateTime.now());

        bookInfoMapper.insert(newBook);
        return Result.success();
    }

    @Override
    public Result listBookChapters(Long bookId, PageReqParams params) {
        IPage<BookChapter> page = new Page<>();
        page.setCurrent(params.getPageNum());
        page.setSize(params.getPageSize());
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq("book_id", bookId)
                .orderByDesc("chapter_num");
        IPage<BookChapter> bookChapterPage = bookChapterMapper.selectPage(page, bookChapterQueryWrapper);

        return Result.success(new PageResp<>(
                bookChapterPage.getCurrent(),
                bookChapterPage.getSize(),
                bookChapterPage.getTotal(),
                bookChapterPage.getRecords()
        ));
    }

    @Override
    public Result saveBookChapter(BookChapter newChapter) {
        //TODO 检验作品是否属于当前作者

        //查询最新章节号
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq("book_id", newChapter.getBookId())
                .orderByDesc("chapter_num")
                .last("limit 1");
        BookChapter lastChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
        if (lastChapter != null) {
            newChapter.setChapterNum(lastChapter.getChapterNum() + 1);
        } else {
            newChapter.setChapterNum(1);
        }
        // 设置章节相关信息
        newChapter.setCreateTime(LocalDateTime.now());
        newChapter.setUpdateTime(LocalDateTime.now());
        newChapter.setWordCount(newChapter.getChapterContent().length());
        bookChapterMapper.insert(newChapter);

        // 更新小说最新章节信息和总字数
        BookInfo bookInfo = bookInfoMapper.selectById(newChapter.getBookId());
        bookInfo.setLastChapterId(newChapter.getId());
        bookInfo.setLastChapterName(newChapter.getChapterName());
        bookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        bookInfo.setWordCount(bookInfo.getWordCount() + newChapter.getWordCount());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfoMapper.updateById(bookInfo);
        return Result.success();
    }

    @Override
    public Result updateBookChapter(Long chapterId, BookChapter chapter) {

        BookChapter oldChapter = bookChapterMapper.selectById(chapterId);

        BookChapter newChapter = new BookChapter();
        newChapter.setId(chapterId);
        newChapter.setChapterName(chapter.getChapterName());
        newChapter.setChapterContent(chapter.getChapterContent());
        newChapter.setUpdateTime(LocalDateTime.now());
        newChapter.setWordCount(chapter.getChapterContent().length());
        bookChapterMapper.updateById(newChapter);

        // 更新小说总字数
        BookInfo bookInfo = bookInfoMapper.selectById(oldChapter.getBookId());
        bookInfo.setUpdateTime(LocalDateTime.now());
        bookInfo.setWordCount(bookInfo.getWordCount() - oldChapter.getWordCount() + newChapter.getWordCount());
        if(Objects.equals(oldChapter.getId(), bookInfo.getLastChapterId())){
            bookInfo.setLastChapterName(newChapter.getChapterName());
            bookInfo.setLastChapterUpdateTime(LocalDateTime.now());
        }
        bookInfoMapper.updateById(bookInfo);
        return Result.success();
    }

    @Override
    public Result listCategory(Integer workDirection) {

        QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_direction", workDirection);
        return Result.success(bookCategoryMapper.selectList(queryWrapper));
    }

    @Override
    public Result addVisitCount(Long bookId) {
        bookInfoMapper.addVisitCount(bookId);
        return Result.success();
    }

    @Override
    public Result listRecBooks(Long bookId) {
        //随机推荐4本小说
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.ne("id", bookId)
                .orderByAsc("rand()")
                .last("limit 4");
        return Result.success(bookInfoMapper.selectList(bookInfoQueryWrapper));
    }

    @Override
    public Result getLastChapterAbout(Long bookId) {
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq("book_id", bookId)
                .orderByDesc("chapter_num")
                .last("limit 1");
        BookChapter lastChapter = bookChapterMapper.selectOne(bookChapterQueryWrapper);
        // 查询章节总数
        bookChapterQueryWrapper.clear();
        bookChapterQueryWrapper.eq("book_id", bookId);
        Long chapterTotal = bookChapterMapper.selectCount(bookChapterQueryWrapper);

        BookChapterAboutRespDto bookChapterAboutRespDto = new BookChapterAboutRespDto();
        bookChapterAboutRespDto.setChapterInfo(lastChapter);
        bookChapterAboutRespDto.setChapterTotal(chapterTotal);
        int chapterContentLength = lastChapter.getChapterContent().length();

        if(chapterContentLength<30){
            bookChapterAboutRespDto.setContentSummary(lastChapter.getChapterContent());
        }else{
            bookChapterAboutRespDto.setContentSummary(lastChapter.getChapterContent().substring(0, 30));
        }
        return Result.success(bookChapterAboutRespDto);
    }

    @Override
    public Result listNewestComments(Long bookId) {
        QueryWrapper<BookComment> commentCountQueryWrapper = new QueryWrapper<>();
        commentCountQueryWrapper.eq("book_id", bookId);
        Long commentTotal = bookCommentMapper.selectCount(commentCountQueryWrapper);
        BookCommentRespDto bookCommentRespDto = new BookCommentRespDto();
        bookCommentRespDto.setCommentTotal(commentTotal);
        if(commentTotal == 0){
            bookCommentRespDto.setComments(new ArrayList<>());
//            return Result.success("暂无评论");
        }

        QueryWrapper<BookComment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("book_id", bookId)
                .orderByDesc("create_time")
                .last("limit 5");
        List<BookComment> bookComments = bookCommentMapper.selectList(commentQueryWrapper);
        //查询评论用户信息
        List<Long> userIds = new ArrayList<>();
        for (BookComment bookComment : bookComments) {
            Long userId = bookComment.getUserId();
            userIds.add(userId);
        }
        List<UserInfo> userInfos = new ArrayList<>();
        if(userIds.size() > 0){
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.in("id", userIds);
            userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        }

        Map<Long, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        List<BookCommentRespDto.CommentInfo> commentInfos = bookComments.stream().map(v ->
            BookCommentRespDto.CommentInfo.builder()
                    .id(v.getId())
                    .commentUserId(v.getUserId())
                    .commentUser(userInfoMap.get(v.getUserId()).getUsername())
                    .commentUserPhoto(userInfoMap.get(v.getUserId()).getUserPhoto())
                    .commentContent(v.getCommentContent())
                    .commentTime(v.getCreateTime())
                    .build()).toList();
        bookCommentRespDto.setComments(commentInfos);
        return Result.success(bookCommentRespDto);
    }
}
