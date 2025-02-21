package com.example.chosunnext.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.user.response
 * fileName       : UserResponseDto
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
public class UserRequestDto {

    private String userId;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String gender;
    private String birth;
    private String phone;
    private String address;
    private String address2;
    private String zipCd;
    private String currentPassword;
    private String newPassword;

}
