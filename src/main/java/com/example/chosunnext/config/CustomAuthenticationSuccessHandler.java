package com.example.chosunnext.config;

import com.example.chosunnext.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
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
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 사용자 정보를 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("user", userDetails);

        // 저장된 요청 정보 확인 (Spring Security가 저장한 이전 요청 URL)
        String savedRequest = (String) session.getAttribute("redirectAfterLogin");
        String redirectUrl = "/"; // 기본값 (메인 페이지)

        if (savedRequest != null) {
            session.removeAttribute("redirectAfterLogin");// 로그인 이전 페이지 URL로 설정

            response.sendRedirect(savedRequest);
        } else {
            // 저장된 URL이 없으면 역할에 따라 기본 이동 페이지 설정
            boolean isReporter = userDetails.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("REPORTER"));

            redirectUrl = isReporter ? "/cms/" : "/";

            // 로그인 후 이전 페이지 또는 기본 페이지로 이동
            response.sendRedirect(redirectUrl);
        }
    }
}
