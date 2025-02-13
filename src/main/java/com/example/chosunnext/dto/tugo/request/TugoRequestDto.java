package com.example.chosunnext.dto.tugo.request;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.tugo.request
 * fileName       : TugoRequestDto
 * author         : 김재홍
 * date           : 25. 2. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 12.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TugoRequestDto {

    private int tugoId;
    private String userId;
    private String title;
    private String content;
    private String fileUrl;

}
