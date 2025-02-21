package com.example.chosunnext.controller;

import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.dto.mypage.response.SubscribedNewsResponseDto;
import com.example.chosunnext.dto.user.request.UserCheckRequestDto;
import com.example.chosunnext.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/library")
    public ResponseEntity<?> getLibraryContent(
            @RequestParam(defaultValue = "recent") String tab,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String userId){
        log.info("RestMypageController.getLibraryContent() userId: {}, tab: {}, page: {}, size: {}", userId, tab, page, size);

        return ResponseEntity.ok(mypageService.getLibraryContent(tab, page, size, userId));
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<SubscribedNewsResponseDto> getSubscribedNews(
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        SubscribedNewsResponseDto response = mypageService.getSubscribedNews(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/info")
    public ResponseEntity<String> updateAccountInfo(@RequestBody UserCheckRequestDto userCheckRequestDto) {
        mypageService.getUserInfo(userCheckRequestDto);
        return ResponseEntity.ok("Update Account Info Success");
    }

}
