package com.example.chosunnext.service;

import com.example.chosunnext.dao.CodeDao;
import com.example.chosunnext.dao.NewsDao;
import com.example.chosunnext.dao.ReporterDao;
import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.file.request.FileDto;
import com.example.chosunnext.dto.user.request.ReporterDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import com.example.chosunnext.utils.FileUtils;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporterService {

    private final ReporterDao reporterDao;
    private final NewsDao newsDao;
    private final FileUtils fileUtils;

    public List<ResponseReporterDto> getReporter() {
        List<ResponseReporterDto> reporters = reporterDao.getReporterDto();
        return reporters;
    }
    @Transactional
    public void saveNews(NewsDto newsDto, List<MultipartFile> files) {
        newsDao.insertNews(newsDto);

        if (files != null && !files.isEmpty()) {
            for(MultipartFile file : files){
                FileDto dto = new FileDto();
                dto.setTugoId(newsDto.getNewsId());
                dto.setFile(file);
                dto.setInstId(newsDto.getUserId());
                dto.setFileGbnCd("100");

                fileUtils.uploadFile(dto);
            }
        }
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
