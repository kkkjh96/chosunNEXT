package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class RestCategoryNewsController {

    final NewsService newsService;



}
