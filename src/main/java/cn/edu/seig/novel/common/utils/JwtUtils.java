package cn.edu.seig.novel.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


@ConditionalOnProperty("novel.jwt.secret")
@Component
public class JwtUtils {

    @Value("${novel.jwt.secret}")
    private String secret;

    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";

    // 根据用户ID生成JWT
    public String generateToken(Long uid, String systemKey) {
        return Jwts.builder()
            .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
            .setSubject(uid.toString())
            .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    // 解析JWT返回用户ID
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
            // 判断该 JWT 是否属于指定系统
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getSubject());
            }
        } catch (JwtException e) {
            System.out.println("JWT解析失败:" + token);
        }
        return null;
    }

}
