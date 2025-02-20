package com.example.chosunnext.dto.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * packageName    : com.example.chosunnext.dto.mypage.response
 * fileName       : MypageMainResonseDto
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
public class MypageMainResonseDto {

    private String username;

    private int point;

    // 읽은 기사수
    private int recentRead;
    // 구독수
    private int subscribed;
    // 북마크수
    private int bookmarked;
    // 투고 한 글 수
    private int tugo;
    // 추천 수
    private int ddabong;
    // 댓글 단 수
    private int commented;
    // 최근 7일 뉴스 읽은 수
    private Map<String, Integer> weeklyViews;

}
