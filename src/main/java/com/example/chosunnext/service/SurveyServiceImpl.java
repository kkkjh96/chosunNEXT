package com.example.chosunnext.service;

import com.example.chosunnext.dao.SurveyDao;
import com.example.chosunnext.dto.survey.request.OptionRequestDto;
import com.example.chosunnext.dto.survey.request.QuestionRequestDto;
import com.example.chosunnext.dto.survey.request.SurveyRequestDto;
import com.example.chosunnext.dto.survey.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : SurveyServiceImpl
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    private final SurveyDao surveyDao;

    @Override
    @Transactional
    public void createSurvey(SurveyRequestDto surveyRequestDto) {

        surveyDao.insertSurveyTitle(surveyRequestDto);

        if (surveyRequestDto.getQuestions() != null) {
            for (QuestionRequestDto questionRequestDto : surveyRequestDto.getQuestions()) {
                questionRequestDto.setTitleId(surveyRequestDto.getTitleId());
                surveyDao.insertSurveyQuestion(questionRequestDto);

                if (!questionRequestDto.getOptions().isEmpty()) {
                    surveyDao.insertSurveyOptions(questionRequestDto.getOptions(), questionRequestDto.getSurveyId());
                }
            }
        }

    }

    @Override
    public List<SurveyTitleResponseDto> getSurveyList() {
        return surveyDao.getSurveyList();
    }

    @Override
    public int deleteSurvey(int surveyId) {
        return surveyDao.deleteSurvey(surveyId);
    }

//    @Override
//    public SurveyResponseDto getSurvey(int titleId) {
//
//        List<SurveyTotalResponseDto> surveyResponseDtoList = surveyDao.getSurvey(titleId);
//
//        SurveyResponseDto surveyResponseDto = new SurveyResponseDto();
//
//        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
//
//        int surveyId = 0;
//
//        QuestionResponseDto questionResponseDto = null;
//
//        for (SurveyTotalResponseDto surveyDto : surveyResponseDtoList) {
//            if(surveyResponseDto.getTitleId() == null){
//                surveyResponseDto.setTitleId(surveyDto.getTitleId());
//                surveyResponseDto.setTitle(surveyDto.getTitle());
//                surveyResponseDto.setContent(surveyDto.getContent());
//            }
//
//            if(surveyDto.getSurveyId() != surveyId){
//
//                questionResponseDto = new QuestionResponseDto();
//                questionResponseDto.setSurveyId(surveyDto.getSurveyId());
//                questionResponseDto.setContent(surveyDto.getSurveyContent());
//                questionResponseDto.setType(surveyDto.getType());
//
//                questionResponseDtoList.add(questionResponseDto);
//                surveyId = surveyDto.getSurveyId();
//
//            }
//
//            if(surveyDto.getOptionId() != null){
//                OptionResponseDto optionResponseDto = new OptionResponseDto();
//                optionResponseDto.setOptionId(surveyDto.getOptionId());
//                optionResponseDto.setContent(surveyDto.getOptionContent());
//                questionResponseDto.getOptions().add(optionResponseDto);
//            }
//        }
//
//        surveyResponseDto.setQuestions(questionResponseDtoList);
//
//        return surveyResponseDto;
//    }

    @Override
    public SurveyResponseDto getSurvey(int titleId) {
        List<SurveyTotalResponseDto> surveyResponseDtoList = surveyDao.getSurvey(titleId);

        SurveyResponseDto surveyResponseDto = new SurveyResponseDto();
        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();

        Integer surveyId = null;
        QuestionResponseDto questionResponseDto = null;

        for (SurveyTotalResponseDto surveyDto : surveyResponseDtoList) {
            // 설문 기본 정보 설정
            if (Objects.isNull(surveyResponseDto.getTitleId())) {
                surveyResponseDto.setTitleId(surveyDto.getTitleId());
                surveyResponseDto.setTitle(surveyDto.getTitle());
                surveyResponseDto.setContent(surveyDto.getContent());
            }

            // 새로운 질문인지 확인
            if (!Objects.equals(surveyDto.getSurveyId(), surveyId)) {
                questionResponseDto = new QuestionResponseDto();
                questionResponseDto.setSurveyId(surveyDto.getSurveyId());
                questionResponseDto.setContent(surveyDto.getSurveyContent());
                questionResponseDto.setType(surveyDto.getType());
                questionResponseDto.setOptions(new ArrayList<>()); // 리스트 초기화

                questionResponseDtoList.add(questionResponseDto);
                surveyId = surveyDto.getSurveyId();
            }

            // 옵션 추가 (questionResponseDto가 null이 아닐 때만)
            if (surveyDto.getOptionId() != null && questionResponseDto != null) {
                OptionResponseDto optionResponseDto = new OptionResponseDto();
                optionResponseDto.setOptionId(surveyDto.getOptionId());
                optionResponseDto.setContent(surveyDto.getOptionContent());
                questionResponseDto.getOptions().add(optionResponseDto);
            }
        }

        surveyResponseDto.setQuestions(questionResponseDtoList);
        return surveyResponseDto;
    }

    @Override
    @Transactional
    public void updateSurvey(int titleId, SurveyRequestDto surveyRequest) {

//        surveyDao.updateSurveyTitle(titleId, surveyRequest.getTitle());

    }
}
