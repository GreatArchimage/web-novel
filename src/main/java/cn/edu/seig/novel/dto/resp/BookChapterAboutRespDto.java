package cn.edu.seig.novel.dto.resp;

import cn.edu.seig.novel.dao.entity.BookChapter;
import lombok.Builder;
import lombok.Data;

@Data
public class BookChapterAboutRespDto {

    private BookChapter chapterInfo;

    /**
     * 章节总数
     */
    private Long chapterTotal;

    /**
     * 内容概要（30字）
     */
    private String contentSummary;

}
