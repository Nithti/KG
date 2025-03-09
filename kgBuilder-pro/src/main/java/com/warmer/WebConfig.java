package com.warmer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置 CORS，允许所有路径（/**）的跨域请求
        registry.addMapping("/**")  // 允许所有路径
                .allowedOrigins("http://localhost:1024")  // 允许前端地址 http://localhost:1024 发起请求
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的 HTTP 方法
                .allowedHeaders("*")  // 允许所有请求头
                .allowCredentials(true);  // 是否允许发送 cookie
    }
}
