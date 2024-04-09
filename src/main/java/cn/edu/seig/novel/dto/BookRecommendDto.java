package cn.edu.seig.novel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BookRecommendDto implements Serializable {
    private Long id;

    /**
     * 类型;1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
     */
    private Integer type;

    private Long bookId;

    private String picUrl;

    private String bookName;

    private String authorName;

    private String intro;

    private Integer sort;

    private Float score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
