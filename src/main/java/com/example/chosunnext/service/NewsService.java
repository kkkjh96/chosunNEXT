package com.example.chosunnext.service;

import com.example.chosunnext.dao.NewsDao;
import com.example.chosunnext.dao.ReporterDao;
import com.example.chosunnext.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsDao newsDao;


    public NewsDto getNewsById(Long newsId) {
            return newsDao.findNewsById(newsId); // newsDao의 함수도 적절한 이름으로 변경
        }
    public boolean updateNews(int newsId, NewsDto updatedNews) {
        updatedNews.setNewsId(newsId);
        int result = newsDao.updateNews(updatedNews);
        return result > 0;
    }

    public int deleteNews(int newsId) {
        int result = newsDao.deleteNews(newsId);
        return result;
    }

    public List<NewsDto> findByCategory(String category, int offset, int size) {
        return newsDao.getCategoryNews(category, offset, size);
    }

    public int countNewsByCategory(String category) {
        return newsDao.countNewsByCategory(category);
    }
}


