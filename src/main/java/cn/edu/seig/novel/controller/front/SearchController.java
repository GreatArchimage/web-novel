package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dto.BookSearchReqDto;
import cn.edu.seig.novel.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/front/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 小说搜索接口
     */
    @GetMapping("books")
    public Result searchBooks(
        @ParameterObject BookSearchReqDto condition) {
        return searchService.searchBooks(condition);
    }

}
