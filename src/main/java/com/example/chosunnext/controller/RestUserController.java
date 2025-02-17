package com.example.chosunnext.controller;


import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import com.example.chosunnext.dto.user.request.RequestUserDto;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import com.example.chosunnext.security.CustomUserDetails;
import com.example.chosunnext.service.CategoryService;
import com.example.chosunnext.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestAPIController
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RestUserController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping("/check-id")
    public ResponseEntity<Boolean> checkId(@RequestBody Map<String, String> request){
        log.info("checkId request : {}", request);

        String userId = request.get("id");
        log.info("userId : {}", userId);

//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);

        Boolean response  = userService.checkId(userId);
        log.info("checkId response : {}", response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request){
        log.info("signup request : {}", request);

        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setUserId(request.get("id"));
        requestUserDto.setPassword(request.get("password"));
        requestUserDto.setNickname(request.get("nickname"));

        userService.signup(requestUserDto);

        ResponseUserDto user = userService.getUser(requestUserDto.getUserId());

        if (user != null) {
            return ResponseEntity.ok("회원가입에 성공하셨습니다.");
        }

        return  ResponseEntity.status(500).body("회원가입에 실패하셨습니다.");
    }

    @GetMapping("/category")
    public ResponseEntity<List<ResponseCategoryDto>> getFooterCategories() {

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();

        log.info(responseCategories.toString());

        return ResponseEntity.ok(responseCategories);
    }

    @GetMapping("/user/me")
    public ResponseEntity<String> getMe(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String userId = authentication.getName();

        return ResponseEntity.ok(userId);
    }



}
