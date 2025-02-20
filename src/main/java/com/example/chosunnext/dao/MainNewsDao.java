package com.example.chosunnext.dao;

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
public interface MainNewsDao {

    NewsResponseDto getImportantNews();

    List<NewsResponseDto> getLatestNews();

    List<NewsResponseDto> getHeadlineNewsMain();

    List<NewsResponseDto> getHotNewsMain();

    List<NewsResponseDto> getEditorPicksMain();

    int updateViewCount(int newsId);

}
