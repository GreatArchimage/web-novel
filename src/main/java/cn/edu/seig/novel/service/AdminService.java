package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.BookCategory;
import cn.edu.seig.novel.dao.entity.SysUser;

public interface AdminService {

    Result login(SysUser sysUser);

    Result listComments(PageReqParams pageReqParams);

    Result deleteComment(Long id);

    Result addBookRecommend(Long bookId, Integer recommendType);

//    Result listBookRecommend();

    Result deleteBookRecommend(Long id);

    Result saveCategory(BookCategory bookCategory);

    Result deleteCategory(Long id);

    Result listSysUsers();

    Result addSysUser(SysUser sysUser);

    Result deleteSysUser(Long id);
}
