package com.example.chosunnext.service;

import com.example.chosunnext.dao.CodeDao;
import com.example.chosunnext.dao.NewsDao;
import com.example.chosunnext.dao.ReporterDao;
import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.user.request.ReporterDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporterService {

    private final ReporterDao reporterDao;
    private final NewsDao newsDao;

    public List<ResponseReporterDto> getReporter() {
        List<ResponseReporterDto> reporters = reporterDao.getReporterDto();
        return reporters;
    }

    public void saveNews(NewsDto newsDto) {
        newsDao.insertNews(newsDto);
    }

    public List<NewsDto> getNewsList() {
        List<NewsDto> newList = newsDao.getAllList();
        System.out.println("리스트 찍어 볼게요: " + newList);
        return newList;
    }

    public int getTotalCount() {
        return newsDao.getTotalCount();
    }

    public List<NewsDto> getPageNews(int offset, int size) {
        return newsDao.getPageNews(offset,size);
    }
}
