package com.example.chosunnext.dto.news.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.news.response
 * fileName       : NewsResponseDto
 * author         : 김재홍
 * date           : 25. 2. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 18.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDto {

    private int newsId;
    private String writer;
    private String mainCategory;
    private String mainCategoryCd;
    private String subCategory;
    private String subCategoryCd;
    private String title;
    private String subTitle;
    private List<String> fileUrls;
    private String createDt;

}
