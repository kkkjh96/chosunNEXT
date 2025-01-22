package com.example.chosunnext.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.user.response
 * fileName       : UserDto
 * author         : 김재홍
 * date           : 25. 1. 22.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 22.        김재홍       최초 생성
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {

    private String userId;
    private String password;
    private String role;

}
