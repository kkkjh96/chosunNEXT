package com.example.chosunnext.dto.survey.response;

import com.example.chosunnext.dto.survey.request.QuestionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.survey.request
 * fileName       : SurveyRequestDto
 * author         : 김재홍
 * date           : 25. 2. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 6.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyResponseDto {

    private Integer titleId;
    private String title;
    private String content;
    private List<QuestionResponseDto> questions;

}
