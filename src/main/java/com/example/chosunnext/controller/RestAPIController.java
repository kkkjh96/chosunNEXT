package com.example.chosunnext.controller;

import com.example.chosunnext.dto.user.request.RequestUserDto;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import com.example.chosunnext.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

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
public class RestAPIController {

    private final UserService userService;

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

}
