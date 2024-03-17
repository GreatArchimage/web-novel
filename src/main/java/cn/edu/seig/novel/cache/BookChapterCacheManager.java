package cn.edu.seig.novel.cache;

import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.mapper.BookChapterMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookChapterCacheManager {
    private final BookChapterMapper bookChapterMapper;

    @Cacheable(value = "bookChapter")
    public BookChapter getBookChapter(Long chapterId) {
         return bookChapterMapper.selectById(chapterId);
    }

    @Cacheable(value = "bookChapterList")
    public List<BookChapter> listChapters(Long bookId) {
        QueryWrapper<BookChapter> bookChapterQueryWrapper = new QueryWrapper<>();
        bookChapterQueryWrapper.eq("book_id", bookId)
                .orderByAsc("chapter_num");
        List<BookChapter> bookChapters = bookChapterMapper.selectList(bookChapterQueryWrapper);
        return bookChapters;
    }
}
