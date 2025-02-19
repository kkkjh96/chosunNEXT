package com.example.chosunnext.service;

import com.example.chosunnext.dao.MainNewsDao;
import com.example.chosunnext.dao.MyNewsDao;
import com.example.chosunnext.dto.news.response.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : MyNewsServiceImpl
 * author         : 김재홍
 * date           : 25. 2. 19.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 19.        김재홍       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MainNewsServiceImpl implements MainNewsService {

    private final MainNewsDao mainNewsDao;

    @Override
    public List<NewsResponseDto> getRecommendedNews() {

        return mainNewsDao.getLatestNews();
    }

    @Override
    public NewsResponseDto getImportantNews() {

        return mainNewsDao.getImportantNews();

    }

    @Override
    public List<NewsResponseDto> getHeadlineNews() {

        return mainNewsDao.getHeadlineNewsMain();
    }

    @Override
    public List<NewsResponseDto> getHotNews() {

        return mainNewsDao.getHotNewsMain();
    }

    @Override
    public List<NewsResponseDto> getEditorPicksNews() {


        return mainNewsDao.getEditorPicksMain();
    }
}
