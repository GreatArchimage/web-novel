package cn.edu.seig.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImgVerifyCodeRespDto {

    /**
     * 当前会话ID，用于标识改图形验证码属于哪个会话
     * */
    private String sessionId;

    /**
     * Base64 编码的验证码图片
     * */
    private String img;

}
