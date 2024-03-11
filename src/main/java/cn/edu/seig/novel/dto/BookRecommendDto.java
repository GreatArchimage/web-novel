package cn.edu.seig.novel.dto;

import lombok.Data;

@Data
public class BookRecommendDto {
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
}
