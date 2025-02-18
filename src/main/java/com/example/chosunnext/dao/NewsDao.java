package com.example.chosunnext.dao;

import com.example.chosunnext.dto.NewsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : NewsDao
 * author         : 박미정
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        박미정      최초 생성
 */
@Mapper
public interface NewsDao {
    int insertNews(NewsDto newsDto);

    List<NewsDto> getAllList();

    NewsDto findNewsById(@Param("newsId") Long newsId);

    int updateNews(NewsDto newsDto);

    int deleteNews(int newsId);

    int getTotalCount();

    List<NewsDto> getPageNews(int offset, int size);

    int countNewsByCategory(String category);

    List<NewsDto> getCategoryNews(String category, int offset, int size);
}


