package com.hniu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //.addMapping("/api/**") 指定请求路径
        registry.addMapping("/**")
                //.allowedOrigins("https://yourdomain.com") //必须指定具体域名！
                .allowedOrigins("*")
                //允许的请求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //允许的头信息
                .allowedHeaders("*");
    }
}
