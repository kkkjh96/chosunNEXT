package com.example.chosunnext.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * packageName    : com.example.chosunnext.config
 * fileName       : CustomAuthenticationSuccessHandler
 * author         : 김재홍
 * date           : 25. 2. 5.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 5.        김재홍       최초 생성
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 인증된 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 사용자 정보를 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("user", userDetails);

        // 사용자 권한 확인 및 리다이렉트 경로 설정
        boolean isReporter = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("REPORTER"));

        if (isReporter) {
            // 리포터 권한이 있는 경우 '/cms'로 리다이렉트
            response.sendRedirect("/cms");
        } else {
            // 일반 사용자 권한인 경우 메인 페이지로 리다이렉트
            response.sendRedirect("/");
        }
    }

}
