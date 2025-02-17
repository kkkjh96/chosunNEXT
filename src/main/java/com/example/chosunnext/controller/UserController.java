package com.example.chosunnext.controller;

import com.example.chosunnext.security.CustomUserDetails;
import com.example.chosunnext.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : UserController
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
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(){
        return "/login/regist";
    }

    @GetMapping("/login")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/survey-form/{surveyId}")
    public String surveyForm(@PathVariable("surveyId") int surveyId){
        return "/survey/survey-form";
    }

    @GetMapping("/side_menu")
    public String index(){
        return "/layout/cms/side_menu";
    }





}
