package com.example.chosunnext.controller;

import com.example.chosunnext.dto.news.request.NewsViewRequestDto;
import com.example.chosunnext.dto.news.response.NewsResponseDto;
import com.example.chosunnext.service.MainNewsService;
import com.example.chosunnext.service.MyNewsService;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final MainNewsService mainNewsService;


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



    @GetMapping("/recommended")
    public ResponseEntity<List<NewsResponseDto>> recommendedMain() {

        List<NewsResponseDto> newsList = mainNewsService.getRecommendedNews();

        log.info("RestNewsControllerMain:{}", newsList);

        return ResponseEntity.ok(newsList != null ? newsList : new ArrayList<>());
    }

    @GetMapping("/Main")
    public ResponseEntity<NewsResponseDto> mainNews() {

        NewsResponseDto newsResponseDto = mainNewsService.getImportantNews();

        return ResponseEntity.ok(newsResponseDto);

    }

    @GetMapping("/headlines")
    public ResponseEntity<List<NewsResponseDto>> headlinesMain() {

        List<NewsResponseDto> newsList = mainNewsService.getHeadlineNews();

        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<NewsResponseDto>> hotNewsMain() {

        List<NewsResponseDto> newsList = mainNewsService.getHotNews();

        log.info("Hot news Main:{}", newsList);

        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/editor-picks")
    public ResponseEntity<List<NewsResponseDto>> editorPicksMain() {
        List<NewsResponseDto> newsList = mainNewsService.getEditorPicksNews();

        log.info("Editor picks news:{}", newsList);

        return ResponseEntity.ok(newsList);
    }

    @PutMapping("/{newsId}/view")
    public ResponseEntity<String> updateViewCount(@PathVariable("newsId") int newsId) {
        int result = mainNewsService.updateViewCount(newsId);

        if(result != 0){
            return ResponseEntity.ok("조회수가 성공적으로 증가하였습니다.");
        }

        return ResponseEntity.badRequest().body("조회수 증가 실패");
    }

    @PutMapping("/saveView")
    public ResponseEntity<String> saveView(@RequestBody NewsViewRequestDto newsViewRequestDto) {

        int result = myNewsService.saveView(newsViewRequestDto);

        if(result != 0){
            return ResponseEntity.ok("조회수 저장 성공");
        }

        return ResponseEntity.badRequest().body("조회수 저장 실��");

    }

}
