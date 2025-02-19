package com.example.chosunnext.service;

import com.example.chosunnext.dao.MyNewsDao;
import com.example.chosunnext.dto.news.response.NewsResponseDto;
import lombok.AllArgsConstructor;
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
public class MyNewsServiceImpl implements MyNewsService {

    private final MyNewsDao myNewsDao;

    @Override
    public List<NewsResponseDto> getRecommendedNews(String username) {

        List<String> content = myNewsDao.getSurveyContents(22, username);

        String likeCate = myNewsDao.getCategory(content.getFirst());

        return myNewsDao.getLatestNewsByCategory(likeCate);
    }

    @Override
    public NewsResponseDto getImportantNews() {

        return myNewsDao.getImportantNews();

    }

    @Override
    public List<NewsResponseDto> getHeadlineNews(String username) {

        List<String> content = myNewsDao.getSurveyContents(22, username);

        return myNewsDao.getHeadlineNews(content);
    }

    @Override
    public List<NewsResponseDto> getHotNews(String username) {

        List<String> content = myNewsDao.getSurveyContents(22, username);

        return myNewsDao.getHotNews(content);
    }

    @Override
    public List<NewsResponseDto> getEditorPicksNews(String username) {

        List<String> content = myNewsDao.getSurveyContents(22, username);

        return myNewsDao.getEditorPicks(content);
    }
}
