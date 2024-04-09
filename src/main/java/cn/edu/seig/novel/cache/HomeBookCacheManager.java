package cn.edu.seig.novel.cache;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.entity.BookRecommend;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.dao.mapper.BookRecommendMapper;
import cn.edu.seig.novel.dto.BookRecommendDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 首页推荐小说 缓存管理类
 */
@Component
@RequiredArgsConstructor
public class HomeBookCacheManager {

    private final BookRecommendMapper bookRecommendMapper;

    private final BookInfoMapper bookInfoMapper;

    /**
     * 查询首页小说推荐，并放入缓存中
     */
    @Cacheable(value = "bookRecommend")
    public List<BookRecommendDto> listHomeBooks() {
        QueryWrapper<BookRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        List<BookRecommend> bookRecommends = bookRecommendMapper.selectList(queryWrapper);

        List<Long> bookIds = new ArrayList<>();
        for (BookRecommend bookRecommend : bookRecommends) {
            Long bookId = bookRecommend.getBookId();
            bookIds.add(bookId);
        }
        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.in("id", bookIds);
        List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

        Map<Long, BookInfo> bookInfoMap = bookInfos.stream().collect(Collectors.toMap(BookInfo::getId, Function.identity()));
        List<BookRecommendDto> bookRecommendDtos = bookRecommends.stream().map(v -> {
            BookInfo bookInfo = bookInfoMap.get(v.getBookId());
            BookRecommendDto bookRecommendDto = new BookRecommendDto();
            bookRecommendDto.setId(v.getId());
            bookRecommendDto.setType(v.getType());
            bookRecommendDto.setBookId(v.getBookId());
            bookRecommendDto.setBookName(bookInfo.getBookName());
            bookRecommendDto.setPicUrl(bookInfo.getPicUrl());
            bookRecommendDto.setAuthorName(bookInfo.getAuthorName());
            bookRecommendDto.setIntro(bookInfo.getIntro());
            bookRecommendDto.setSort(v.getSort());
            bookRecommendDto.setScore(bookInfo.getScore());
            bookRecommendDto.setCreateTime(v.getCreateTime());
            bookRecommendDto.setUpdateTime(v.getUpdateTime());
            return bookRecommendDto;
        }).collect(Collectors.toList());

        return bookRecommendDtos;
    }

}
