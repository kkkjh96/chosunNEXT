package com.example.chosunnext.controller;

import com.example.chosunnext.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestNewsController
 * author         : 김재홍
 * date           : 25. 2. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 18.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class RestNewsController {

    private final SurveyService surveyService;

    @GetMapping("/{userId}/survey-status")
    public ResponseEntity<Boolean> getSurveyStatus(@PathVariable("userId") String userId) {
        log.info("RestNewsController.getSurveyStatus() - userId: {}", userId);

        Boolean result = surveyService.surveyStatus(userId);

        log.info("Rest:{}", result);

        return ResponseEntity.ok(result);
    }



}
