package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.entity.BookRecommend;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.dao.mapper.BookRecommendMapper;
import cn.edu.seig.novel.dto.BookRecommendDto;
import cn.edu.seig.novel.service.HomeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final BookInfoMapper bookInfoMapper;

    private final BookRecommendMapper bookRecommendMapper;

    @Override
    public Result listHomeBooks() {
        QueryWrapper<BookRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        List<BookRecommend> bookRecommends = bookRecommendMapper.selectList(queryWrapper);

        if(bookRecommends.isEmpty()){
            return Result.success("没有数据");
        }

        List<Long> bookIds = new ArrayList<>();
        for (BookRecommend bookRecommend : bookRecommends) {
            Long bookId = bookRecommend.getBookId();
            bookIds.add(bookId);
        }

        QueryWrapper<BookInfo> bookInfoQueryWrapper = new QueryWrapper<>();
        bookInfoQueryWrapper.in("id", bookIds);
        List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoQueryWrapper);

        if(bookInfos.isEmpty()){
            return Result.success("没有数据");
        }

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
            return bookRecommendDto;
        }).collect(Collectors.toList());

        return Result.success(bookRecommendDtos);
    }

}
