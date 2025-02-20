package com.example.chosunnext.dto.mypage.response;

import com.example.chosunnext.dto.NewsDto;
import lombok.*;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : SubscribedNewsResponseDto
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
public class SubscribedNewsResponseDto {

    private List<NewsDto> data;
    private int totalCount;
    private int totalPages;

}
