package com.example.chosunnext.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto
 * fileName       : NewsDto
 * author         : 박미정
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        박미정      최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private String title;
    private String sub_title;
    private String main_category_cd;
    private String sub_category_cd;
    private String reservation_time;
    private String news_type;
}
