package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.service.ReporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
@Slf4j
public class RestCmsController {

    private final ReporterService reporterService;

    @PostMapping("news")
    public ResponseEntity<String> createNews(@RequestBody NewsDto newsDto) {

        System.out.println("다들어갓나?"+newsDto);
        reporterService.saveNews(newsDto);

        return ResponseEntity.ok("기사 등록 성공!");
    }


}
