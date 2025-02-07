package com.example.chosunnext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : com.example.chosunnext.config
 * fileName       : WebConfig
 * author         : 김재홍
 * date           : 25. 2. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 7.        김재홍       최초 생성
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addConfiguration(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:7070", "http://192.168.0.15:7070")  // 여러 Origin 추가
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);  // 인증 정보를 포함할 경우 true로 설정
    }

}
