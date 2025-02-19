package com.example.chosunnext.config;

import com.example.chosunnext.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * packageName    : com.example.chosunnext.config
 * fileName       : Security
 * author         : 김재홍
 * date           : 25. 1. 22.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 22.        김재홍       최초 생성
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * 권한 접두사 제거 설정
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");  // ROLE_ 접두사 제거
    }

    /**
     * PasswordEncoder 빈 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 등록
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * DaoAuthenticationProvider 설정
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * SecurityFilterChain 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔹 정적 리소스에 대한 보안 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/uploads/**").permitAll()  // 🔥 이미지 요청은 인증 없이 허용
                        .requestMatchers("/css/**", "/javascript/**", "/images/**", "/error/**").permitAll()
                        .requestMatchers("/survey/**", "/smarteditor/**", "/admin/**", "/categoryNews/**").permitAll()
                        .requestMatchers("/login", "/", "/register", "/api/**", "/board/list", "/board/detail/**").permitAll()
                        .requestMatchers("/mypage/**", "/survey-form/**", "/board/write", "/board/edit/**").hasAuthority("USER")  // 사용자 권한 검사
                        .anyRequest().authenticated()
                )

                // 🔹 이미지 요청 시 새로운 세션 생성 방지
                .sessionManagement(session -> session
                        .sessionFixation().none()  // 기존 세션 유지
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 필요할 때만 세션 생성
                )

                // 🔹 보안 헤더 설정
                .headers(headers -> headers
//                        .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
//                                "default-src 'self'; " +
//                                        "img-src 'self' data: http://localhost:7070/uploads/; " +
//                                        "script-src 'self' 'unsafe-inline' 'unsafe-eval' http: https:; " +
//                                        "style-src 'self' 'unsafe-inline' http: https:;"))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // 동일 출처에서 iframe 허용
                )

                // 🔹 CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // 🔹 로그인 설정
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("user_id")  // 사용자 아이디 필드명 설정
                        .passwordParameter("password") // 비밀번호 필드명 설정
                        .successHandler(customAuthenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // 🔹 로그아웃 설정 (세션 유지)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)  // 🔥 로그아웃 시 세션 유지
                        .deleteCookies("JSESSIONID")
                )

                // 🔹 예외 처리 설정
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/error");
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
