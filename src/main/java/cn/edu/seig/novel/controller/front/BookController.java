package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookComment;
import cn.edu.seig.novel.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/front/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("category/list")
    public Result listCategory(Integer workDirection) {
        System.out.println(workDirection);
        return bookService.listCategory(workDirection);
    }

    /**
     * 小说信息查询接口
     */
    @GetMapping("{id}")
    public Result getBookById(@PathVariable("id") Long bookId) {
        return bookService.getBookById(bookId);
    }

    /**
     * 增加小说点击量接口
     */
    @PostMapping("visit")
    public Result addVisitCount(Long bookId) {
        return bookService.addVisitCount(bookId);
    }

    /**
     * 小说推荐列表查询接口
     */
    @GetMapping("rec_list")
    public Result listRecBooks(Long bookId){
        return bookService.listRecBooks(bookId);
    }

    /**
     * 小说点击榜查询接口
     */
    @GetMapping("visit_rank")
    public Result listVisitRankBooks() {
        return bookService.listVisitRankBooks();
    }

    /**
     * 小说新书榜查询接口
     */

    @GetMapping("newest_rank")
    public Result listNewestRankBooks() {
        return bookService.listNewestRankBooks();
    }

    /**
     * 小说更新榜查询接口
     */

    @GetMapping("update_rank")
    public Result listUpdateRankBooks() {
        return bookService.listUpdateRankBooks();
    }

    /**
     * 小说最新章节相关信息查询接口
     */
    @GetMapping("last_chapter/about")
    public Result getLastChapterAbout(Long bookId) {
        return bookService.getLastChapterAbout(bookId);
    }

    /**
     * 小说最新评论查询接口
     */
    @GetMapping("comment/newest_list")
    public Result listNewestComments(Long bookId) {
        return bookService.listNewestComments(bookId);
    }

    @GetMapping("content/{chapterId}")
    public Result getBookContentAbout(@PathVariable("chapterId") Long chapterId) {
        return bookService.getBookContentAbout(chapterId);
    }

    @GetMapping("chapter/list")
    public Result listChapters(Long bookId) {
        return bookService.listChapters(bookId);
    }

    @GetMapping("pre_chapter_id/{chapterId}")
    public Result getPreChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.getPreChapterId(chapterId);
    }

    @GetMapping("next_chapter_id/{chapterId}")
    public Result getNextChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.getNextChapterId(chapterId);
    }

//    @PostMapping("comment")
//    public Result saveComment(@RequestBody BookComment bookComment) {
//        dto.setUserId(UserHolder.getUserId());
//        return bookService.saveComment(dto);
//    }

}
