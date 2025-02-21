package com.example.chosunnext.controller;

import com.example.chosunnext.dto.BookmarkDto;
import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import com.example.chosunnext.service.BookmarkService;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.ReporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class RestCategoryNewsController {

    final NewsService newsService;
    final BookmarkService bookmarkService;
    final ReporterService reporterService;

    @PostMapping("/news")
    public ResponseEntity<String> saveBookmark(@RequestBody BookmarkDto bookmarkDto) {
        boolean isBookmarked = bookmarkService.saveBookmark(bookmarkDto);
        return ResponseEntity.ok(isBookmarked ? "북마크 저장 완료" : "북마크 삭제 완료");
    }


    @GetMapping("/news")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam int newsId, @RequestParam String userId) {
        boolean isBookmarked = bookmarkService.isBookmarked(newsId, userId);
        return ResponseEntity.ok(isBookmarked);
    }

    @DeleteMapping("/news")
    public ResponseEntity<String> deleteBookmark(@RequestBody BookmarkDto bookmarkDto) {
        boolean deleted = bookmarkService.deleteBookmark(bookmarkDto);
        return ResponseEntity.ok(deleted ? "북마크 삭제 완료" : "북마크 삭제 실패");
    }


    @GetMapping("/news/detail/{newsId}")
    public ResponseEntity<NewsDto> getNews(@PathVariable("newsId") int newsId) {
        System.out.println(newsId+"sdfsdfsdfsdf");
         NewsDto news  = newsService.getDetailNews(newsId);

        return ResponseEntity.ok(news);
    }


    @GetMapping("/reporter/{newsId}")
    public ResponseEntity<List<ResponseReporterDto>> getReporter(@PathVariable("newsId") int newsId ){
        List<ResponseReporterDto>  reporter = reporterService.getReporterWithDepartment(newsId);
        System.out.println(reporter+"sdfsdfsdfsdf!!!!!!!!!!!!!!!!");
        return ResponseEntity.ok(reporter);
    }







}
