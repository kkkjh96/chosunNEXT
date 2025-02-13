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
    NewsDto insertNews(NewsDto newsDto);

    List<NewsDto> getAllList();

    NewsDto findNewsById(@Param("news_id") Long news_id);

    int updateNews(NewsDto updatedNews);
}
