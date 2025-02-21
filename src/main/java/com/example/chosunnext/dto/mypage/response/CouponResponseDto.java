package com.example.chosunnext.dto.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : CouponResponseDto
 * author         : 김재홍
 * date           : 25. 2. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 21.        김재홍       최초 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponseDto {

    private String couponNum;
    private String couponStatus;

}
