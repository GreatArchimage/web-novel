package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.common.utils.PageResp;
import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.mapper.BookChapterMapper;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

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
        return null;
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
}
