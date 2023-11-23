package com.ssafy.config;

import com.ssafy.interceptor.NotAuthorizedInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173")
//                .allowedOrigins("http://192.168.130.64:5173")
//                .allowedOrigins("http://192.168.130.63:5173")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*");
//                .maxAge(-1);

        // 모든 메서드를 허용한다고 명시하지 않으면 기본적인 메서드(GET, POST)만 허용한다.
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       /*
        registry.addInterceptor(new BeforeLoginInterceptor())

                .addPathPatterns("/logout", "/user", "/board");
        // 로그인 하지 않았을 때 접근할 수 없는 경로 추가

        registry.addInterceptor(new AfterLoginInterceptor())
                .addPathPatterns("/login", "/user", "/board");
        // 로그인 했을 때 접근할 수 없는 경로 추가
        */

        registry.addInterceptor(new NotAuthorizedInterceptor()).addPathPatterns("/**");
    }
}