package cn.edu.seig.novel.controller.admin;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.service.AdminService;
import cn.edu.seig.novel.service.BookService;
import cn.edu.seig.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody SysUser sysUser) {
        return adminService.login(sysUser);
    }

    @GetMapping("/book/list")
    public Result listBooks() {
        return bookService.listBooks();
    }

    /*
    查询用户列表
     */
    @GetMapping("/user/list")
    public Result listUsers() {
        return userService.listUsers();
    }

    /*
    查询所有评论
     */
    @GetMapping("/comment/list")
    public Result listComments(PageReqParams pageReqParams) {
        return adminService.listComments(pageReqParams);
    }

    /*
    删除评论
     */
    @DeleteMapping("/comment/{id}")
    public Result deleteComment(@PathVariable Long id) {
        return adminService.deleteComment(id);
    }
}
