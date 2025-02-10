package com.example.chosunnext.dao;

import com.example.chosunnext.dto.survey.request.*;
import com.example.chosunnext.dto.survey.response.*;
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

    void updateSurveyTitle(SurveyRequestDto survey);

    List<QuestionResponseDto> getQuestionByTitleId(@Param("titleId")int titleId);

    void updateQuestion(@Param("question") QuestionRequestDto question);

    List<OptionResponseDto> getOptionBySurveyId(@Param("surveyId")int surveyId);

    void updateOption(@Param("option") OptionRequestDto option);

    void insertOption(@Param("option") OptionRequestDto option);

    void deleteQuestion(int surveyId);

    void deleteOption(int optionId);

    void insertSurveyResult(@Param("result") SubmitRequestDto result);

    void insertSurveyOptionResult(@Param("option") SubmitOptionsRequestDto option);

}
