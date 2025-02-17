package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.ReporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        reporterService.saveNews(newsDto);
        return ResponseEntity.ok("기사 등록 성공!");
    }


    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsDto> newsList = reporterService.getNewsList();
        return ResponseEntity.ok(newsList);  // JSON 데이터 반환
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<?> getNewsDetail(@PathVariable("newsId") Long newsId) {
        NewsDto news = newsService.getNewsById(newsId);
        return ResponseEntity.ok(news);
    }

    @GetMapping("/news/paged")
    public ResponseEntity<Map<String, Object>> getPageNews(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("size") int size) {

        int totalCount = reporterService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        int offset = (currentPage - 1) * size;
        List<NewsDto> newsList = reporterService.getPageNews(offset, size);

        Map<String, Object> response = new HashMap<>();
        response.put("totalCount", totalCount);
        response.put("totalPages", totalPages);
        response.put("currentPage", currentPage);
        response.put("newsList", newsList);
        response.put("offset", offset);
        response.put("size", size);

        return ResponseEntity.ok(response);
    }



    @PutMapping("/news/{newsId}")
    public ResponseEntity<String> updateNews(@PathVariable int newsId,@RequestBody NewsDto newsDto) {
        boolean isUpdate = newsService.updateNews(newsId,newsDto);
        if(isUpdate) {
            return ResponseEntity.ok("뉴스가 성공적으로 수정");
        }else{
            return ResponseEntity.ok("수정 실패");
        }
    }


    @DeleteMapping("/news/{news_id}")
    public ResponseEntity<String> deleteNews(@PathVariable int news_id) {
         int news = newsService.deleteNews(news_id);
         if(news > 0) {
             System.out.println("삭제 성공");
             log.info("뉴스 삭제 성공");
            }
        return ResponseEntity.ok("수정 성공");
    }


















}
