package cn.edu.seig.novel.service;


import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dto.BookSearchReqDto;

public interface SearchService {

    Result searchBooks(BookSearchReqDto condition);

}
