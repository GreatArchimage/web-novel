package cn.edu.seig.novel.controller.front;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/front/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    /**
     * 获取图片验证码接口
     */
//    @Operation(summary = "获取图片验证码接口")
//    @GetMapping("img_verify_code")
//    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
//        return resourceService.getImgVerifyCode();
//    }

    /**
     * 图片上传接口
     */
    @PostMapping("/image")
    Result uploadImage(@RequestParam("file") MultipartFile file) {
        return resourceService.uploadImage(file);
    }

}
