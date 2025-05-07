package com.example.common;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: youMeng
 * @Date: 2025/4/9 - 04 - 09 - 20:08
 * @Description: com.example.common
 * @version: 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/files/download/**");
    }

    @Bean
    public JWTInterceptor jwtInterceptor() {
        return new JWTInterceptor();
    }
}
