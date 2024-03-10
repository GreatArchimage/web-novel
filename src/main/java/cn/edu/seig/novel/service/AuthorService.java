package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.AuthorInfo;

public interface AuthorService {


    Result getAuthorStatus(Long userId);

    Result registerAuthor(AuthorInfo authorInfo);
}
