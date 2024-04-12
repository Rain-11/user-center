package com.crazy.rain.usercenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            //是否发送Cookie
            .allowCredentials(true)
            //放行哪些原始域
            .allowedOrigins("前端项目ip地址 allowCredentials 为false情况下可以直接填 * ")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("*");
    }
}