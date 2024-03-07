package cn.edu.seig.novel.dto;

import lombok.Data;

@Data
public class UserRegisterReqDto {

    private String username;

    private String password;

    private String velCode;

    /**
     * 请求会话标识，用来标识图形验证码属于哪个会话
     * */
    private String sessionId;

}
