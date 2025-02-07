package com.example.chosunnext.dto.survey.request;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.user.request
 * fileName       : OptionRequestDto
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
public class OptionRequestDto {

    private int optionId;
    private int surveyId;
    private String content;

}

