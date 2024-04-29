package cn.edu.seig.novel.common.utils;

import lombok.Data;

@Data
public class PageReqParams {

    /**
     * 请求页码，默认第 1 页
     */
    private int pageNum = 1;

    /**
     * 每页大小，默认每页 10 条
     */
    private int pageSize = 10;

    private boolean fetchAll = false;

}