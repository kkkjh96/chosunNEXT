package com.example.chosunnext.service;

import com.example.chosunnext.dao.NewsDao;
import com.example.chosunnext.dao.ReporterDao;
import com.example.chosunnext.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsDao newsDao;


    public NewsDto getNewsById(Long news_id) {
            return newsDao.findNewsById(news_id); // newsDao의 함수도 적절한 이름으로 변경
        }
    public boolean updateNews(int news_id, NewsDto updatedNews) {
        updatedNews.setNews_id(news_id);
        int result = newsDao.updateNews(updatedNews);
        return result > 0;
    }

    public int deleteNews(int news_id) {
        int result = newsDao.deleteNews(news_id);
        return result;
    }
}


