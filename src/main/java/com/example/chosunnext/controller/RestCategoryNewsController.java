package com.example.chosunnext.controller;

import com.example.chosunnext.dto.BookmarkDto;
import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.service.BookmarkService;
import com.example.chosunnext.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class RestCategoryNewsController {

    final NewsService newsService;
    final BookmarkService bookmarkService;

    @PostMapping("/news")
    public ResponseEntity<String> saveBookmark(@RequestBody BookmarkDto bookmarkDto) {
        boolean isBookmarked = bookmarkService.saveBookmark(bookmarkDto);
        return ResponseEntity.ok(isBookmarked ? "북마크 저장 완료" : "북마크 삭제 완료");
    }

}
