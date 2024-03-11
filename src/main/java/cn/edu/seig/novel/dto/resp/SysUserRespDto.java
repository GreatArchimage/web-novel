package cn.edu.seig.novel.dto.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SysUserRespDto {

    private Long uid;

    private String username;

    private String token;
}
