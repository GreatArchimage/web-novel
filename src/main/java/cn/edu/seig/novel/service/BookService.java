package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.BookChapter;
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

    Result listCategory(Integer workDirection);

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
//
//    /**
//     * 获取上一章节ID
//     *
//     * @param chapterId 章节ID
//     * @return 上一章节ID
//     */
//    Result<Long> getPreChapterId(Long chapterId);
//
//    /**
//     * 获取下一章节ID
//     *
//     * @param chapterId 章节ID
//     * @return 下一章节ID
//     */
//    Result<Long> getNextChapterId(Long chapterId);
//
//    /**
//     * 小说章节列表查询
//     *
//     * @param bookId 小说ID
//     * @return 小说章节列表
//     */
//    Result<List<BookChapterRespDto>> listChapters(Long bookId);
//
//    /**
//     * 小说分类列表查询
//     *
//     * @param workDirection 作品方向;0-男频 1-女频
//     * @return 分类列表
//     */
//    Result<List<BookCategoryRespDto>> listCategory(Integer workDirection);
//
//    /**
//     * 发表评论
//     *
//     * @param dto 评论相关 DTO
//     * @return void
//     */
//    Result<Void> saveComment(UserCommentReqDto dto);
//
//    /**
//     * 小说最新评论查询
//     *
//     * @param bookId 小说ID
//     * @return 小说最新评论数据
//     */
//    Result<BookCommentRespDto> listNewestComments(Long bookId);
//
//    /**
//     * 删除评论
//     *
//     * @param userId    评论用户ID
//     * @param commentId 评论ID
//     * @return void
//     */
//    Result<Void> deleteComment(Long userId, Long commentId);
//
//    /**
//     * 修改评论
//     *
//     * @param userId  用户ID
//     * @param id      评论ID
//     * @param content 修改后的评论内容
//     * @return void
//     */
//    Result<Void> updateComment(Long userId, Long id, String content);
//
//    /**
//     * 小说信息保存
//     *
//     * @param dto 小说信息
//     * @return void
//     */
//    Result<Void> saveBook(BookAddReqDto dto);
//
//    /**
//     * 小说章节信息保存
//     *
//     * @param dto 章节信息
//     * @return void
//     */
//    Result<Void> saveBookChapter(ChapterAddReqDto dto);
//
//    /**
//     * 查询作家发布小说列表
//     *
//     * @param dto 分页请求参数
//     * @return 小说分页列表数据
//     */
//    Result<PageRespDto<BookInfoRespDto>> listAuthorBooks(PageReqDto dto);
//
//    /**
//     * 查询小说发布章节列表
//     *
//     * @param bookId 小说ID
//     * @param dto    分页请求参数
//     * @return 章节分页列表数据
//     */
//    Result<PageRespDto<BookChapterRespDto>> listBookChapters(Long bookId, PageReqDto dto);
//
//    /**
//     * 分页查询评论
//     *
//     * @param userId     会员ID
//     * @param pageReqDto 分页参数
//     * @return 评论分页列表数据
//     */
//    Result<PageRespDto<UserCommentRespDto>> listComments(Long userId, PageReqDto pageReqDto);
//
//    /**
//     * 小说章节删除
//     *
//     * @param chapterId 章节ID
//     * @return void
//     */
//    Result<Void> deleteBookChapter(Long chapterId);
//
//    /**
//     * 小说章节查询
//     *
//     * @param chapterId 章节ID
//     * @return 章节内容
//     */
//    Result<ChapterContentRespDto> getBookChapter(Long chapterId);
//
//    /**
//     * 小说章节更新
//     *
//     * @param chapterId 章节ID
//     * @param dto       更新内容
//     * @return void
//     */
//    Result<Void> updateBookChapter(Long chapterId, ChapterUpdateReqDto dto);
}
