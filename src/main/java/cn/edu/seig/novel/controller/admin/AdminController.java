package cn.edu.seig.novel.controller.admin;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.service.BookService;
import cn.edu.seig.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

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
}
