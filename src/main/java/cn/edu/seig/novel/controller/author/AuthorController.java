package cn.edu.seig.novel.controller.author;

import cn.edu.seig.novel.auth.UserHolder;
import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.AuthorInfo;
import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.service.AuthorService;
import cn.edu.seig.novel.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final BookService bookService;

    private final AuthorService authorService;

    @GetMapping("status")
    public Result getAuthorStatus() {
        return authorService.getAuthorStatus(UserHolder.getUserId());
    }

    @PostMapping("register")
    public Result registerAuthor(@RequestBody AuthorInfo authorInfo) {
        authorInfo.setUserId(UserHolder.getUserId());
        return authorService.registerAuthor(authorInfo);
    }

    @GetMapping("info")
    public Result getAuthorInfo() {
        return authorService.getAuthorInfo(UserHolder.getUserId());
    }

    @PutMapping("info")
    public Result updateAuthorInfo(@RequestBody AuthorInfo authorInfo) {
        authorInfo.setUserId(UserHolder.getUserId());
        return authorService.updateAuthorInfo(authorInfo);
    }

    @GetMapping("/book/list")
    public Result listBooks(@ParameterObject PageReqParams pageReqParams) {
        return bookService.listAuthorBooks(pageReqParams);
    }

    @PostMapping("book")
    public Result publishBook(@RequestBody BookInfo newBook) {
        return bookService.saveBook(newBook);
    }

    @GetMapping("book/chapters/{bookId}")
    public Result listBookChapters(@PathVariable("bookId") Long bookId, @ParameterObject PageReqParams pageReqParams) {
        return bookService.listBookChapters(bookId, pageReqParams);
    }

    @PostMapping("book/chapter/{bookId}")
    public Result addBookChapter(@PathVariable("bookId") Long bookId, @RequestBody BookChapter newChapter) {
        newChapter.setBookId(bookId);
        return bookService.saveBookChapter(newChapter);
    }

    @PutMapping("book/chapter/{chapterId}")
    public Result updateBookChapter(@PathVariable("chapterId") Long chapterId,
            @RequestBody BookChapter chapter) {
        return bookService.updateBookChapter(chapterId, chapter);
    }
}
