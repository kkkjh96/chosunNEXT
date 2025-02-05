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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestUserDto loginRequest, HttpSession session) {
        try {
            // 사용자 인증 수행
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(),
                            loginRequest.getPassword()
                    )
            );

            // 인증 성공 시 SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 사용자 정보를 세션에 저장
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            log.info("User Details : {}", userDetails);
            session.setAttribute("user", SecurityContextHolder.getContext());
//            session.setAttribute("user", userDetails);

            return ResponseEntity.ok("로그인 성공");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("로그인 실패: 이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    private final CategoryService categoryService;

    @GetMapping("/footer/categories")
    public ResponseEntity<List<ResponseCategoryDto>> getFooterCategories() {

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();

        log.info(responseCategories.toString());

        return ResponseEntity.ok(responseCategories);
    }

}
