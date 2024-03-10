package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.auth.UserHolder;
import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.BookComment;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dto.UserRegisterReqDto;
import cn.edu.seig.novel.service.BookService;
import cn.edu.seig.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/front/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final BookService bookService;

    /**
     * 用户注册接口
     */
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterReqDto dto) {
        return userService.register(dto);
    }

    /**
     * 用户登录接口
     */
    @PostMapping("login")
    public Result login(@RequestBody UserInfo userInfo) {
        return userService.login(userInfo);
    }

    @PostMapping("comment")
    public Result saveComment(@RequestBody BookComment bookComment) {
        bookComment.setUserId(UserHolder.getUserId());
        return bookService.saveComment(bookComment);
    }

    @GetMapping("bookshelf")
    public Result getBookshelfContent() {
        return bookService.getBookshelfContent(UserHolder.getUserId());
    }

    @PostMapping("bookshelf/{bookId}")
    public Result addBookToBookshelf(@PathVariable("bookId") Long bookId) {
        return bookService.addBookToBookshelf(UserHolder.getUserId(), bookId);
    }

    @GetMapping("bookshelf/have")
    public Result haveBookInBookshelf(Long bookId) {
        return bookService.haveBookInBookshelf(UserHolder.getUserId(), bookId);
    }

    @DeleteMapping("bookshelf/{bookId}")
    public Result removeBookFromBookshelf(@PathVariable("bookId") Long bookId) {
        return bookService.removeBookFromBookshelf(UserHolder.getUserId(), bookId);
    }

    @GetMapping
    public Result getUserInfo() {
        return userService.getUserInfo(UserHolder.getUserId());
    }

    @PutMapping
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
        userInfo.setId(UserHolder.getUserId());
        return userService.updateUserInfo(userInfo);
    }


}
