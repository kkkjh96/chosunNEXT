package com.example.chosunnext.service;

import com.example.chosunnext.dao.SurveyDao;
import com.example.chosunnext.dto.survey.request.*;
import com.example.chosunnext.dto.survey.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        log.info(String.valueOf(surveyRequest));

        surveyDao.updateSurveyTitle(surveyRequest);

        List<QuestionResponseDto> oldQuestion = surveyDao.getQuestionByTitleId(titleId);

        for (QuestionRequestDto question : surveyRequest.getQuestions()) {
            if(question.getSurveyId() != 0){
                question.setTitleId(surveyRequest.getTitleId());
                surveyDao.updateQuestion(question);
                updateOptions(question);
            } else {
                question.setTitleId(surveyRequest.getTitleId());
                surveyDao.insertSurveyQuestion(question);
                updateOptions(question);

                if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                    surveyDao.insertSurveyOptions(question.getOptions(), question.getSurveyId());
                }
            }
        }

        // 기존 질문 중 새 요청에 없는 질문은 삭제
        handleDeletedQuestions(oldQuestion, surveyRequest.getQuestions());

    }

    private void updateOptions(QuestionRequestDto question) {

        List<OptionResponseDto> oldOptions = surveyDao.getOptionBySurveyId(question.getSurveyId());

        for (OptionRequestDto option : question.getOptions()){
            if(option.getOptionId() != 0){
                option.setSurveyId(question.getSurveyId());
                surveyDao.updateOption(option);
            }else{
                option.setSurveyId(question.getSurveyId());
                surveyDao.insertOption(option);
            }
        }

        handleDeletedOptions(oldOptions, question.getOptions());

    }

    private void handleDeletedQuestions(List<QuestionResponseDto> oldQuestions, List<QuestionRequestDto> newQuestions) {
        Set<Integer> newQuestionIds = newQuestions.stream()
                .map(QuestionRequestDto::getSurveyId)
                .collect(Collectors.toSet());

        for (QuestionResponseDto existingQuestion : oldQuestions) {
            if (!newQuestionIds.contains(existingQuestion.getSurveyId())) {
                // 새로운 요청에 없는 기존 질문은 삭제
                surveyDao.deleteQuestion(existingQuestion.getSurveyId());
            }
        }
    }

    /**
     * 기존 옵션 목록에서 요청에 없는 옵션을 삭제합니다.
     *
     * @param oldOptions 기존 옵션 목록
     * @param newOptions 요청으로 받은 새로운 옵션 목록
     */
    private void handleDeletedOptions(List<OptionResponseDto> oldOptions, List<OptionRequestDto> newOptions) {
        Set<Integer> newOptionIds = newOptions.stream()
                .map(OptionRequestDto::getOptionId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (OptionResponseDto existingOption : oldOptions) {
            if (!newOptionIds.contains(existingOption.getOptionId())) {
                // 새로운 요청에 없는 기존 옵션은 삭제
                surveyDao.deleteOption(existingOption.getOptionId());
            }
        }
    }

    @Override
    @Transactional
    public void submitSurvey(int titleId, SubmitRequestDto submitRequestDto) {

        surveyDao.insertSurveyResult(submitRequestDto);

        if (submitRequestDto.getSubmitOptionsList() != null) {
            for (SubmitOptionsRequestDto optionsRequestDto : submitRequestDto.getSubmitOptionsList()) {
                log.info("ASDF " +optionsRequestDto);
                optionsRequestDto.setResultId(submitRequestDto.getResultId());
                optionsRequestDto.setJoinedValues(String.join(",", optionsRequestDto.getOptionValue()));
                log.info("ZXCV " +optionsRequestDto);
                surveyDao.insertSurveyOptionResult(optionsRequestDto);
            }
        }

    }

}
