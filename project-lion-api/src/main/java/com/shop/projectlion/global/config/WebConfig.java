package com.shop.projectlion.global.config;

import com.shop.projectlion.global.resolver.AuthenticationInterceptor;
import com.shop.projectlion.global.resolver.AdminAuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminAuthorizationInterceptor requestRoleInterceptor;

    private final AuthenticationInterceptor requestHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderInterceptor)
            .order(1)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/health/**","/api/oauth/login","/api/token","/api/logout");
        registry.addInterceptor(requestRoleInterceptor)
            .order(2)
            .addPathPatterns("/api/admin/**")
            .excludePathPatterns();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) { // todo cors 설정 관련 학습 후 적당한 요청 설정
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name()
                );
    }

}