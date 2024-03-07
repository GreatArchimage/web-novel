package cn.edu.seig.novel.service.impl;


import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageResp;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dao.mapper.BookInfoMapper;
import cn.edu.seig.novel.dto.BookSearchReqDto;
import cn.edu.seig.novel.service.SearchService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final BookInfoMapper bookInfoMapper;

    @Override
    public Result searchBooks(BookSearchReqDto condition) {
        Page<BookInfo> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(page, condition);


        return Result.success(new PageResp<>(condition.getPageNum(),condition.getPageSize(),page.getTotal(),bookInfos));
    }
}
