package com.example.chosunnext.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.user.request
 * fileName       : UserDto
 * author         : 김재홍
 * date           : 25. 1. 22.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 22.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUserDto {

    private String userId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String gender;
    private String birth;
    private String phone;
    private String address;
    private String address2;
    private String zipCd;
    private String role;

}
