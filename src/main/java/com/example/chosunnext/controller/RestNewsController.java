package com.example.chosunnext.controller;

import com.example.chosunnext.dto.news.response.NewsResponseDto;
import com.example.chosunnext.service.MyNewsService;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestNewsController
 * author         : 김재홍
 * date           : 25. 2. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 18.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class RestNewsController {

    private final SurveyService surveyService;
    private final MyNewsService myNewsService;

    @GetMapping("/{userId}/survey-status")
    public ResponseEntity<Boolean> getSurveyStatus(@PathVariable("userId") String userId) {
        log.info("RestNewsController.getSurveyStatus() - userId: {}", userId);

        Boolean result = surveyService.surveyStatus(userId);

        log.info("Rest:{}", result);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/recommended/{username}")
    public ResponseEntity<List<NewsResponseDto>> recommended(@PathVariable("username") String username) {

        List<NewsResponseDto> newsList = myNewsService.getRecommendedNews(username);

        log.info("RestNewsController:{}", newsList);

        return ResponseEntity.ok(newsList != null ? newsList : new ArrayList<>());
    }

    @GetMapping("/myMain")
    public ResponseEntity<NewsResponseDto> myMain() {

        NewsResponseDto newsResponseDto = myNewsService.getImportantNews();

        return ResponseEntity.ok(newsResponseDto);

    }

    @GetMapping("/headlines/{username}")
    public ResponseEntity<List<NewsResponseDto>> headlines(@PathVariable("username") String username) {

        List<NewsResponseDto> newsList = myNewsService.getHeadlineNews(username);

        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/hot/{username}")
    public ResponseEntity<List<NewsResponseDto>> hotNews(@PathVariable("username") String username) {

        List<NewsResponseDto> newsList = myNewsService.getHotNews(username);

        log.info("Hot news:{}", newsList);

        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/editor-picks/{username}")
    public ResponseEntity<List<NewsResponseDto>> editorPicks(@PathVariable("username") String username) {
        List<NewsResponseDto> newsList = myNewsService.getEditorPicksNews(username);

        log.info("Editor picks news:{}", newsList);

        return ResponseEntity.ok(newsList);
    }

}
