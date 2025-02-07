package com.example.chosunnext.dao;

import com.example.chosunnext.dto.survey.request.OptionRequestDto;
import com.example.chosunnext.dto.survey.request.QuestionRequestDto;
import com.example.chosunnext.dto.survey.request.SurveyRequestDto;
import com.example.chosunnext.dto.survey.response.SurveyResponseDto;
import com.example.chosunnext.dto.survey.response.SurveyTitleResponseDto;
import com.example.chosunnext.dto.survey.response.SurveyTotalResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : SurveyDao
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
@Mapper
public interface SurveyDao {

    void insertSurveyTitle(SurveyRequestDto survey);

    void insertSurveyQuestion(QuestionRequestDto question);

    void insertSurveyOptions(@Param("options") List<OptionRequestDto> options, @Param("surveyId") int surveyId);

    List<SurveyTitleResponseDto> getSurveyList();

    int deleteSurvey(int surveyId);

    List<SurveyTotalResponseDto> getSurvey(int titleId);

}
