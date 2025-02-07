package com.example.chosunnext.dto.survey.response;

import com.example.chosunnext.dto.survey.request.OptionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.survey.request
 * fileName       : QuestionRequestDto
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
public class QuestionResponseDto {
    private int surveyId;
    private int titleId;
    private String content;
    private String type;
    private List<OptionResponseDto> options;
}
