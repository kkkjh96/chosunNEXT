package com.example.chosunnext.dto.news.request;

import lombok.*;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.chosunnext.dto.news.request
 * fileName       : NewsViewRequestDto
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
public class NewsViewRequestDto {

    private int newsId;
    private String userId;

}
