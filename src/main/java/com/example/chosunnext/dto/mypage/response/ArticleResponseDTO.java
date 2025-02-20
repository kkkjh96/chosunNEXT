package com.example.chosunnext.dto.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : ArticleResponseDTO
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseDTO {

    private int newsId;
    private String title;
    private String content;
    private int viewCount;
    private String date;
    private String image;

}
