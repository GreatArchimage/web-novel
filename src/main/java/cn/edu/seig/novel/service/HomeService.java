package cn.edu.seig.novel.service;


import cn.edu.seig.novel.common.http.Result;

import java.util.List;


public interface HomeService {

    /**
     * 查询首页小说推荐列表
     */
    Result listHomeBooks();

}
