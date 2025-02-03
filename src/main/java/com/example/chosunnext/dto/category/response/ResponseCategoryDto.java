package com.example.chosunnext.dto.category.response;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.category.response
 * fileName       : ResponseCategoryDto
 * author         : 김재홍
 * date           : 25. 2. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 3.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCategoryDto {

    private String cd;
    private String cdKor;
    private String parentCd;

}
