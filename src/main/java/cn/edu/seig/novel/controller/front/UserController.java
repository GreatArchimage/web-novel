package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.dao.entity.UserInfo;
import cn.edu.seig.novel.dto.UserRegisterReqDto;
import cn.edu.seig.novel.service.BookService;
import cn.edu.seig.novel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/front/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    private final BookService bookService;

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

}
