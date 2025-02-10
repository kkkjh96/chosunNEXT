package com.example.chosunnext.dto.survey.request;

import lombok.*;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.survey.request
 * fileName       : SubmitOptionsRequestDto
 * author         : 김재홍
 * date           : 25. 2. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 10.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOptionsRequestDto {

    private int surveyId;
    private int resultId;
    private List<String> optionValue;
    private String joinedValues;

}
