package com.example.chosunnext.dto.user.request;

import lombok.*;

/**
 * packageName    : com.example.chosunnext.dto.user.request
 * fileName       : UserCheckRequestDto
 * author         : 김재홍
 * date           : 25. 2. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 21.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCheckRequestDto {

    private String userId;
    private String password;

}
