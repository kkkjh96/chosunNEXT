package com.example.chosunnext.controller;

import com.example.chosunnext.dto.mypage.response.CouponResponseDto;
import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.dto.mypage.response.SubscribedNewsResponseDto;
import com.example.chosunnext.dto.user.request.UserCheckRequestDto;
import com.example.chosunnext.dto.user.request.UserRequestDto;
import com.example.chosunnext.dto.user.response.UserResponseDto;
import com.example.chosunnext.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

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
    public ResponseEntity<UserResponseDto> updateAccountInfo(@RequestBody UserCheckRequestDto userCheckRequestDto) {

        UserResponseDto user = mypageService.getUserInfo(userCheckRequestDto);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/account/update")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserRequestDto userRequestDto) {

        int result = mypageService.updateUserInfo(userRequestDto);

        if(result != 0 ){
            return ResponseEntity.ok("성공적으로 정보가 수정되었습니다.");
        }

        return ResponseEntity.ok().build();
    }

    // ✅ 비밀번호 변경
    @PutMapping("/account/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserRequestDto user) {

        try {

            mypageService.updatePassword(user);
            return ResponseEntity.ok("비밀번호 변경 완료");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    // ✅ 회원 탈퇴
    @DeleteMapping("/account/delete")
    public ResponseEntity<?> deleteUserAccount(@RequestBody UserRequestDto user) {

        log.info("Delete user account:{}", user);

        try {

            mypageService.deleteUserAccount(user);

            return ResponseEntity.ok("회원 탈퇴 완료");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(@RequestParam String userId) {

        return ResponseEntity.ok(mypageService.getOrders(userId));

    }

    @PostMapping("/coupon")
    public ResponseEntity<Map<String, String>> registerCoupon(@RequestBody CouponResponseDto coupon) {

        log.info("coupon:{}", coupon);

        String message = mypageService.registerCoupon(coupon.getCouponNum());

        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }

}
