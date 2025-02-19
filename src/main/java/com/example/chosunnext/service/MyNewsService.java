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
public interface MyNewsService {

    List<NewsResponseDto> getRecommendedNews(String username);

    NewsResponseDto getImportantNews();

    List<NewsResponseDto> getHeadlineNews(String username);

    List<NewsResponseDto> getHotNews(String username);

    List<NewsResponseDto> getEditorPicksNews(String username);
}
