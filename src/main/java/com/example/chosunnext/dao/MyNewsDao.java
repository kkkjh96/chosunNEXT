package com.example.chosunnext.dao;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.news.request.NewsViewRequestDto;
import com.example.chosunnext.dto.news.response.NewsResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : MyNewsDao
 * author         : 김재홍
 * date           : 25. 2. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 19.        김재홍       최초 생성
 */
@Mapper
public interface MyNewsDao {

    List<String> getSurveyContents(@Param("resultId") int resultId, @Param("participant") String participant);

    String getCategory(String category);

    NewsResponseDto getImportantNews();

    List<NewsResponseDto> getLatestNewsByCategory(@Param("mainCategoryCd") String mainCategoryCd);

    List<NewsResponseDto> getHeadlineNews(@Param("categories") List<String> categories);

    List<NewsResponseDto> getHotNews(@Param("categories") List<String> categories);

    List<NewsResponseDto> getEditorPicks(@Param("categories") List<String> categories);

    int upsertViewRecord(@Param("record")NewsViewRequestDto newsViewRequestDto);


}
