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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporterService {

    private final ReporterDao reporterDao;
    private final NewsDao newsDao;

    public List<ResponseReporterDto> getReporter() {
        List<ResponseReporterDto> reporters = reporterDao.getReporterDto();
        
        System.out.println("리스트 ㅇㅇㅇ"+reporters);
        return reporters;
    }

    public NewsDto saveNews(NewsDto newsDto) {
        NewsDto news = newsDao.insertNews(newsDto);
        System.out.println("디비 들어가나 "+news);
        return news;
    }
}
