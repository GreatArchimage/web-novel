package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
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
//
//    /**
//     * 小说最新章节相关信息查询接口
//     */
//    @Operation(summary = "小说最新章节相关信息查询接口")
//    @GetMapping("last_chapter/about")
//    public RestResp<BookChapterAboutRespDto> getLastChapterAbout(
//        @Parameter(description = "小说ID") Long bookId) {
//        return bookService.getLastChapterAbout(bookId);
//    }
//
    /**
     * 小说推荐列表查询接口
     */
    @GetMapping("rec_list")
    public Result listRecBooks(Long bookId){
        return bookService.listRecBooks(bookId);
    }
//
//    /**
//     * 小说章节列表查询接口
//     */
//    @Operation(summary = "小说章节列表查询接口")
//    @GetMapping("chapter/list")
//    public RestResp<List<BookChapterRespDto>> listChapters(
//        @Parameter(description = "小说ID") Long bookId) {
//        return bookService.listChapters(bookId);
//    }
//
//    /**
//     * 小说内容相关信息查询接口
//     */
//    @Operation(summary = "小说内容相关信息查询接口")
//    @GetMapping("content/{chapterId}")
//    public RestResp<BookContentAboutRespDto> getBookContentAbout(
//        @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
//        return bookService.getBookContentAbout(chapterId);
//    }
//
//    /**
//     * 获取上一章节ID接口
//     */
//    @Operation(summary = "获取上一章节ID接口")
//    @GetMapping("pre_chapter_id/{chapterId}")
//    public RestResp<Long> getPreChapterId(
//        @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
//        return bookService.getPreChapterId(chapterId);
//    }
//
//    /**
//     * 获取下一章节ID接口
//     */
//    @Operation(summary = "获取下一章节ID接口")
//    @GetMapping("next_chapter_id/{chapterId}")
//    public RestResp<Long> getNextChapterId(
//        @Parameter(description = "章节ID") @PathVariable("chapterId") Long chapterId) {
//        return bookService.getNextChapterId(chapterId);
//    }

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

}
