package com.example.chosunnext.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : MainController
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping("/")
    public String index() {
        log.info("MainController.index()");
        return "/main";
    }

}
