package com.example.chosunnext.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : AdminController
 * author         : 박미정
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        박미정      최초 생성
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String adminMain(){
        return "/admin/main";
    }

    @GetMapping("/userlist")
    public String userList(){
        return "/admin/user_list";
    }
}
