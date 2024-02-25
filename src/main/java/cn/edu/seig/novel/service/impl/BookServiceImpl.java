package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInfoMapper bookInfoMapper;

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
}
