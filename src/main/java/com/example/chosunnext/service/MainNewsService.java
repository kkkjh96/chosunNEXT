package com.example.chosunnext.service;

import com.example.chosunnext.dto.news.response.NewsResponseDto;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : MyNewsService
 * author         : 김재홍
 * date           : 25. 2. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 19.        김재홍       최초 생성
 */
public interface MainNewsService {

    List<NewsResponseDto> getRecommendedNews();

    NewsResponseDto getImportantNews();

    List<NewsResponseDto> getHeadlineNews();

    List<NewsResponseDto> getHotNews();

    List<NewsResponseDto> getEditorPicksNews();

    int updateViewCount(int newsId);
}
