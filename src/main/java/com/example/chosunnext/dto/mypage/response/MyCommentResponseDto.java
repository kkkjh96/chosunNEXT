package com.example.chosunnext.dto.mypage.response;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : MyCommentResponseDto
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
public class MyCommentResponseDto {

    private String title;
    private String contents;
    private String date;

}
