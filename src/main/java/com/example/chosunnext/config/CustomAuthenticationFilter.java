package com.example.chosunnext.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * packageName    : com.example.chosunnext.config
 * fileName       : SecurityFilterChain
 * author         : 김재홍
 * date           : 25. 2. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 17.        김재홍       최초 생성
 */
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/login") && !requestURI.startsWith("/css") && !requestURI.startsWith("/javascript")
                && !requestURI.startsWith("/api") && !requestURI.startsWith("/upload") && !requestURI.startsWith("/images")
                && !requestURI.startsWith("/")) {
            session.setAttribute("redirectAfterLogin", request.getRequestURI());
        }

        filterChain.doFilter(request, response);

    }
}
