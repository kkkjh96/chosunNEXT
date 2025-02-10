package com.example.chosunnext.controller;

import com.example.chosunnext.dto.survey.request.SurveyRequestDto;
import com.example.chosunnext.dto.survey.response.SurveyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : SurveyController
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
@Controller
@RequestMapping("/survey")
@RequiredArgsConstructor
@Slf4j
public class SurveyController {

    @GetMapping("/create")
    public String surveyCreate() {
        return "/survey/survey-create";
    }

    @GetMapping("/list")
    public String surveyList() {
        return "/survey/survey-list";
    }

    @GetMapping("/view/{surveyId}")
    public String surveyView(@PathVariable("surveyId") int surveyId) {
        return "/survey/survey-view";
    }

    @GetMapping("/edit/{surveyId}")
    public String surveyEdit(@PathVariable("surveyId") int surveyId) {
        return "/survey/survey-edit";
    }


}
