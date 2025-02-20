package com.example.chosunnext.dto.mypage.response;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : SubscriptionNewsResponseDto
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
public class NewsResponseDto {

    private int newsId;
    private String title;
    private String writer;
    private String credateDt;
    private String fileUrl;

}
