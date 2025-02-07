package com.example.chosunnext.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.example.chosunnext.config
 * fileName       : CustomErrorController
 * author         : 김재홍
 * date           : 25. 2. 5.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 5.        김재홍       최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.info("Error occurred: status code {}", statusCode);
        if (statusCode != null) {
            log.error("Error occurred: status code {}, request URI: {}", statusCode, request.getRequestURI());

            if (statusCode == 403) {
                model.addAttribute("message", "권한이 없습니다. 접근이 거부되었습니다.");
                return "error/403";  // 슬래시 제거 후 반환
            }
        }

        model.addAttribute("message", "예상치 못한 오류가 발생했습니다.");
        return "error/default";
    }

}
