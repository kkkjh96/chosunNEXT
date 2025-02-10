package com.example.chosunnext.dto.survey.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.survey.request
 * fileName       : SubmitRequestDto
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
public class SubmitRequestDto {

    private int titleId;
    private String userId;
    private int resultId;
    private List<SubmitOptionsRequestDto> submitOptionsList;

}
