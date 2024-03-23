package cn.edu.seig.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BookshelfContentRespDto implements Serializable {

    private Long id;
    private String picUrl;
    private Long bookId;
    private String bookName;
    private Long authorId;
    private String authorName;
    private Long preChapterId;
    private String preChapterName;

    private LocalDateTime updateTime;
}
