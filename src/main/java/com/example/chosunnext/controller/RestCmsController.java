package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.ReporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class RestCmsController {

    private final ReporterService reporterService;
    private final NewsService newsService;

    @PostMapping("/news")
    public ResponseEntity<String> createNews(@RequestBody NewsDto newsDto) {

        System.out.println("다들어갓나?"+newsDto);
        reporterService.saveNews(newsDto);

        return ResponseEntity.ok("기사 등록 성공!");
    }


    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsDto> newsList = reporterService.getNewsList();
        System.out.println(newsList +"sdf");
        return ResponseEntity.ok(newsList);  // JSON 데이터 반환
    }

    @GetMapping("/news/{news_id}")
    public ResponseEntity<?> getNewsDetail(@PathVariable("news_id") Long news_id) {
        NewsDto news = newsService.getNewsById(news_id);
        System.out.println("뉴스아이디"+news);
        return ResponseEntity.ok(news);
    }

}
