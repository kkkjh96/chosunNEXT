package com.example.chosunnext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:7070", "http://192.168.0.15:7070")  // 여러 Origin 추가
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowCredentials(true);  // 인증 정보를 포함할 경우 true로 설정
        registry.addMapping("/**")             // 모든 경로에 대해 CORS 설정
                .allowedOrigins("*")           // 모든 출처 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
                .allowedHeaders("*")           // 모든 요청 헤더 허용
                .allowCredentials(true)        // 인증 정보(쿠키, Authorization 등) 허용
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 핸들러 추가
        // '/uploads/**'로 시작하는 요청을 프로젝트 루트의 'uploads' 폴더로 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/static/uploads/");
    }

}
