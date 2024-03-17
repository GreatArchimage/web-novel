package cn.edu.seig.novel.cache;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.entity.UserBookshelf;
import cn.edu.seig.novel.dao.mapper.BookChapterMapper;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.dao.mapper.UserBookshelfMapper;
import cn.edu.seig.novel.dto.resp.BookInfoRespDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookInfoCacheManager {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    private final UserBookshelfMapper userBookshelfMapper;

    @Cacheable(value = "bookVisitRank")
    public List<BookInfo> listVisitRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("visit_count").last("limit 30");
        return bookInfoMapper.selectList(bookInfoQueryWrapper);
    }

    @Cacheable(value = "bookNewestRank")
    public List<BookInfo> listNewestRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("create_time").last("limit 30");
        return bookInfoMapper.selectList(bookInfoQueryWrapper);
    }

    @Cacheable(value = "bookUpdateRank")
    public List<BookInfo> listUpdateRankBooks() {
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.orderByDesc("update_time").last("limit 30");
        return bookInfoMapper.selectList(bookInfoQueryWrapper);
    }

    @Cacheable(value = "bookInfo")
    public BookInfo getBookInfo(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        return bookInfo;
    }

    @Cacheable(value = "bookShelf")
    public List<BookInfo> getBooksFromBookShelf(Long userId) {
        QueryWrapper<UserBookshelf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserBookshelf> userBookshelves = userBookshelfMapper.selectList(queryWrapper);
        // 查询书架小说信息
        List<Long> bookIds = userBookshelves.stream().map(UserBookshelf::getBookId).toList();
        List<BookInfo> bookInfos = new ArrayList<>();
        if(bookIds.size() > 0){
            QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
            bookInfoQueryWrapper.in("id", bookIds);
            bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);
        }
        return bookInfos;
    }

    @CacheEvict(value = "bookShelf", key = "#userId")
    public void evictBookShelfCache(Long userId) {

    }
}
