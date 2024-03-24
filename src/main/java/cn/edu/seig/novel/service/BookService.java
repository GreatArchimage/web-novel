package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.entity.BookComment;
import cn.edu.seig.novel.dao.entity.BookInfo;

import java.security.NoSuchAlgorithmException;

public interface BookService {

    /**
     * 小说点击榜查询
     *
     * @return 小说点击排行列表
     */
    Result listVisitRankBooks();

    /**
     * 小说新书榜查询
     *
     * @return 小说新书排行列表
     */
    Result listNewestRankBooks();

    /**
     * 小说更新榜查询
     *
     * @return 小说更新排行列表
     */
    Result listUpdateRankBooks();

    /**
     * 小说信息查询
     *
     * @param bookId 小说ID
     * @return 小说信息
     */
    Result getBookById(Long bookId);

    Result listBooks();

    Result saveBook(BookInfo newBook);

    Result listBookChapters(Long bookId, PageReqParams params);

    Result saveBookChapter(BookChapter newChapter);

    Result updateBookChapter(Long chapterId, BookChapter chapter);

    Result listCategory();

//
//    /**
//     * 小说推荐列表查询
//     *
//     * @param bookId 小说ID
//     * @return 小说信息列表
//     */
//    Result<List<BookInfoRespDto>> listRecBooks(Long bookId) throws NoSuchAlgorithmException;
//
    /**
     * 增加小说点击量
     *
     * @param bookId 小说ID
     * @return 成功状态
     */
    Result addVisitCount(Long bookId);

    Result listRecBooks(Long bookId);

    Result getLastChapterAbout(Long bookId);

    Result listNewestComments(Long bookId);

    Result getBookContentAbout(Long chapterId);

    Result listChapters(Long bookId);

    Result getPreChapterId(Long chapterId);

    Result getNextChapterId(Long chapterId);

    Result saveComment(BookComment bookComment);

    Result getBookshelfContent(Long userId);

    Result addBookToBookshelf(Long userId, Long bookId);

    Result haveBookInBookshelf(Long userId, Long bookId);

    Result removeBookFromBookshelf(Long userId, Long bookId);

    Result listAuthorBooks(PageReqParams params);
}
