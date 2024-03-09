package cn.edu.seig.novel.dto.resp;

import cn.edu.seig.novel.dao.entity.BookChapter;
import cn.edu.seig.novel.dao.entity.BookInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 小说内容相关 响应DTO
 */
@Data
@Builder
public class BookContentAboutRespDto {

    /**
     * 小说信息
     */
    private BookInfo bookInfo;

    /**
     * 章节信息
     */
    private BookChapter chapterInfo;

    /**
     * 章节内容
     */
    private String bookContent;

}
