package cn.edu.seig.novel.service;

import cn.edu.seig.novel.common.http.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResourceService {

    /**
     * 获取图片验证码
     *
     * @throws IOException 验证码图片生成失败
     * @return Base64编码的图片
     */
    Result getImgVerifyCode() throws IOException;

    /**
     * 图片上传
     * @param file 需要上传的图片
     * @return 图片访问路径
     * */
    Result uploadImage(MultipartFile file);
}
