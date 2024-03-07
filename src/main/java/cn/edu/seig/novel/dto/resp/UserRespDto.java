package cn.edu.seig.novel.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRespDto {

    private Long uid;

    private String nickName;

    private String token;
}
