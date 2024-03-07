package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/front/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    /**
     * 首页小说推荐查询接口
     */
    @GetMapping("books")
    public Result listHomeBooks() {
        return homeService.listHomeBooks();
    }

}
