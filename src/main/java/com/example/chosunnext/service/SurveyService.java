package com.example.chosunnext.service;

import com.example.chosunnext.dto.survey.request.SubmitRequestDto;
import com.example.chosunnext.dto.survey.request.SurveyRequestDto;
import com.example.chosunnext.dto.survey.response.SurveyResponseDto;
import com.example.chosunnext.dto.survey.response.SurveyTitleResponseDto;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : SurveyService
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
public interface SurveyService {

    void createSurvey(SurveyRequestDto surveyRequestDto);

    List<SurveyTitleResponseDto> getSurveyList();

    int deleteSurvey(int surveyId);

    SurveyResponseDto getSurvey(int titleId);

    void updateSurvey(int titleId, SurveyRequestDto surveyRequest);

    void submitSurvey(int titleId, SubmitRequestDto submitRequestDto);

    boolean surveyStatus(String userId);
}
