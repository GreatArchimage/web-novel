package cn.edu.seig.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("book_info")
public class BookInfo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer workDirection;

    private Long categoryId;

    private String categoryName;

    private String picUrl;

    private String bookName;

    private Long authorId;

    private String authorName;

    private String intro;

    private Integer score;

    private Integer bookStatus;

    private Long visitCount;

    private Integer wordCount;

    private Integer commentCount;

    private Long lastChapterId;

    private String lastChapterName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastChapterUpdateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWorkDirection() {
        return workDirection;
    }

    public void setWorkDirection(Integer workDirection) {
        this.workDirection = workDirection;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Long getLastChapterId() {
        return lastChapterId;
    }

    public void setLastChapterId(Long lastChapterId) {
        this.lastChapterId = lastChapterId;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public LocalDateTime getLastChapterUpdateTime() {
        return lastChapterUpdateTime;
    }

    public void setLastChapterUpdateTime(LocalDateTime lastChapterUpdateTime) {
        this.lastChapterUpdateTime = lastChapterUpdateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", workDirection=" + workDirection +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", bookName='" + bookName + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", intro='" + intro + '\'' +
                ", score=" + score +
                ", bookStatus=" + bookStatus +
                ", visitCount=" + visitCount +
                ", wordCount=" + wordCount +
                ", commentCount=" + commentCount +
                ", lastChapterId=" + lastChapterId +
                ", lastChapterName='" + lastChapterName + '\'' +
                ", lastChapterUpdateTime=" + lastChapterUpdateTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
