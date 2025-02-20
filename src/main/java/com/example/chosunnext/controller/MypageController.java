package com.example.chosunnext.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : MypageController
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mypage")
public class MypageController {

    @GetMapping("")
    public String mypage() {
        return "/mypage/mypage-main";
    }

    @GetMapping("/library")
    public String myLibrary() {
        return "/mypage/library"; // 보관함
    }

    @GetMapping("/subscriptions")
    public String mySubscriptions() {
        return "/mypage/subscriptions"; // 구독함
    }

    @GetMapping("/account")
    public String myAccount() {
        return "/mypage/account"; // 계정 정보
    }

    @GetMapping("/orders")
    public String myOrders() {
        return "/mypage/orders"; // 결제 내역
    }

    @GetMapping("/coupons")
    public String myCoupons() {
        return "/mypage/coupons"; // 쿠폰함
    }

}
