package cn.edu.seig.novel.controller.author;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final BookService bookService;

    @PostMapping("book")
    public Result publishBook(@RequestBody BookInfo newBook) {
        return bookService.saveBook(newBook);
    }
}
