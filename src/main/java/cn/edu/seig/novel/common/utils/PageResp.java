package cn.edu.seig.novel.common.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResp<T> {

    /**
     * 页码
     */
    private final long pageNum;

    /**
     * 每页大小
     */
    private final long pageSize;

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页数据
     */
    private final List<? extends T> list;

    public PageResp(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
