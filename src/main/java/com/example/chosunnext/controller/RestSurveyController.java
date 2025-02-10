package com.example.chosunnext.controller;

import com.example.chosunnext.dto.survey.request.SubmitRequestDto;
import com.example.chosunnext.dto.survey.request.SurveyRequestDto;
import com.example.chosunnext.dto.survey.response.SurveyResponseDto;
import com.example.chosunnext.dto.survey.response.SurveyTitleResponseDto;
import com.example.chosunnext.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestSurveyController
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
@Slf4j
public class RestSurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<String> createSurvey(@RequestBody SurveyRequestDto surveyRequest) {

        log.info("a{}", surveyRequest);
        try{
            surveyService.createSurvey(surveyRequest);
            return ResponseEntity.ok("설문이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("설문 생성에 실패하였습니다.");
        }

    }

    @GetMapping("/list")
    public List<SurveyTitleResponseDto> getSurveyList() {
        return surveyService.getSurveyList();
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<String> deleteSurvey(@PathVariable("surveyId") int surveyId) {
        int result = surveyService.deleteSurvey(surveyId);

        if(result != 0){
            return ResponseEntity.ok("설문이 성공적으로 삭제되었습니다.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("설문이 삭제되지 않았습니다.");
    }

    @GetMapping("/{surveyId}")
    public SurveyResponseDto getSurveyTitle(@PathVariable("surveyId") int titleId) {
        return surveyService.getSurvey(titleId);
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<String> updateSurvey(@PathVariable("surveyId") int surveyId, @RequestBody SurveyRequestDto surveyRequest) {
        log.info(String.valueOf(surveyRequest));
        surveyService.updateSurvey(surveyId, surveyRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{titleId}/submit")
    public ResponseEntity<String> submitSurvey(@PathVariable("titleId") int titleId, @RequestBody SubmitRequestDto submitRequestDto) {
        log.info("QWERTY " + submitRequestDto);

        surveyService.submitSurvey(titleId, submitRequestDto);

        return ResponseEntity.ok().build();
    }

}
