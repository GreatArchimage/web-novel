package cn.edu.seig.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentListItemRespDto {

    private Long id;
    private String commentContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;
    private Long userId;
    private Long bookId;
    private String username;

    private String bookName;

}
