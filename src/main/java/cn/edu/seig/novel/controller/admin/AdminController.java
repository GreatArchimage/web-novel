package cn.edu.seig.novel.controller.admin;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.common.utils.PageReqParams;
import cn.edu.seig.novel.dao.entity.BookCategory;
import cn.edu.seig.novel.dao.entity.SysUser;
import cn.edu.seig.novel.service.AdminService;
import cn.edu.seig.novel.service.BookService;
import cn.edu.seig.novel.service.HomeService;
import cn.edu.seig.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final BookService bookService;
    private final UserService userService;
    private final HomeService homeService;

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
    禁用用户
     */
    @PutMapping("/user/disable/{id}")
    public Result disableUser(@PathVariable Long id) {
        return adminService.disableUser(id);
    }

    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
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

    /*
    添加小说推荐
     */
    @PostMapping("/book/recommend")
    public Result addBookRecommend(Long bookId, Integer recommendType) {
        return adminService.addBookRecommend(bookId,recommendType);
    }

    @GetMapping("/book/recommend/list")
    public Result listBookRecommend() {
//        return adminService.listBookRecommend();
        return homeService.listHomeBooks();
    }

    @DeleteMapping("/book/recommend/{id}")
    public Result deleteBookRecommend(@PathVariable Long id) {
        return adminService.deleteBookRecommend(id);
    }

    @GetMapping("category/list")
    public Result listCategory() {
        return bookService.listCategory();
    }

    @PostMapping("category")
    public Result saveCategory(@RequestBody BookCategory bookCategory) {
        return adminService.saveCategory(bookCategory);
    }

    @DeleteMapping("category/{id}")
    public Result deleteCategory(@PathVariable Long id) {
        return adminService.deleteCategory(id);
    }

    @GetMapping("book/chapters/{bookId}")
    public Result listBookChapters(@PathVariable("bookId") Long bookId, @ParameterObject PageReqParams pageReqParams) {
        return bookService.listBookChapters(bookId, pageReqParams);
    }

    @GetMapping("/sysUser/list")
    public Result listSysUsers() {
        return adminService.listSysUsers();
    }

    @PostMapping("/sysUser")
    public Result addSysUser(@RequestBody SysUser sysUser) {
        return adminService.addSysUser(sysUser);
    }

    @DeleteMapping("/sysUser/{id}")
    public Result deleteSysUser(@PathVariable Long id) {
        return adminService.deleteSysUser(id);
    }
}
