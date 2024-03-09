package cn.edu.seig.novel.config;

import cn.edu.seig.novel.interceptor.AuthInterceptor;
import cn.edu.seig.novel.interceptor.TokenParseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web Mvc 相关配置不要加 @EnableWebMvc 注解，否则会导致 jackson 的全局配置失效。因为 @EnableWebMvc 注解会导致
 * WebMvcAutoConfiguration 自动配置失效
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    private final TokenParseInterceptor tokenParseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 权限认证拦截
        registry.addInterceptor(authInterceptor)
            // 拦截会员中心相关请求接口
            .addPathPatterns("/api/front/user" + "/**",
                // 拦截作家后台相关请求接口
                    "/api/author" + "/**",
                // 拦截平台后台相关请求接口
                    "/api/admin" + "/**")
            // 放行登录注册相关请求接口
            .excludePathPatterns("/api/front/user" + "/register",
                "/api/front/user" + "/login",
                    "/api/admin" + "/login")
            .order(2);

        // Token 解析拦截器
//        registry.addInterceptor(tokenParseInterceptor)
//            // 拦截小说内容查询接口，需要解析 token 以判断该用户是否有权阅读该章节（付费章节是否已购买）
//            .addPathPatterns(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX + "/content/*")
//            .order(3);

    }
}
