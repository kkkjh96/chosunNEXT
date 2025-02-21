package com.example.chosunnext.dto.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : OrdersResponseDto
 * author         : 김재홍
 * date           : 25. 2. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 21.        김재홍       최초 생성
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {

    private int payId;
    private String userId;
    private String productName;
    private int cost;
    private String orderDt;
    private String expireDate;
    private String subscriptionPeriod;
}
