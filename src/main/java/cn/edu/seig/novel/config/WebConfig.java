package cn.edu.seig.novel.config;

import cn.edu.seig.novel.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 权限认证拦截
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/front/user" + "/**",
                    "/api/author" + "/**",
                    "/api/admin" + "/**",
                    "/api/front/book/content" + "/**")
            .excludePathPatterns("/api/front/user" + "/register",
                "/api/front/user" + "/login",
                    "/api/admin" + "/login")
            .order(2);

    }
}
