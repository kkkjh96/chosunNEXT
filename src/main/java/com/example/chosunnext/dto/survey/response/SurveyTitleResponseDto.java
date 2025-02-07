package com.example.chosunnext.dto.survey.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.example.chosunnext.dto.survey.response
 * fileName       : SurveyResponseDto
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
public class SurveyTitleResponseDto {

    private int titleId;
    private String title;
    private String content;
    private Instant createDt;
    private Instant updateDt;

}
