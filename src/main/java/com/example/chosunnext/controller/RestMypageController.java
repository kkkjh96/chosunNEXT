package com.example.chosunnext.controller;

import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestMypageController
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Slf4j
public class RestMypageController {

    private final MypageService mypageService;

    @GetMapping("/{userId}")
    public ResponseEntity<MypageMainResonseDto> mypage(@PathVariable("userId") String userId) {

        log.info("RestMypageController.mypage() userId: {}", userId);

        MypageMainResonseDto mypage = mypageService.getUserActivity(userId);

        return ResponseEntity.ok(mypage);
    }

}
