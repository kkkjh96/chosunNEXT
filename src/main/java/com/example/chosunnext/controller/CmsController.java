package com.example.chosunnext.controller;

import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import com.example.chosunnext.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cms")
public class CmsController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String cms() {
        return "/cms/main";
    }

    @GetMapping("/regist")
    public String cmsRegistPage(Model model) {
        // 대분류, 소분류, 기자명 데이터를 가져옴
        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();

        // 로깅
        log.info("카테고리 목록: {}", responseCategories);

        // 모델에 데이터 추가 (키와 값을 제대로 설정)
        model.addAttribute("categories", responseCategories);

        // 뷰 페이지 반환 (예: regist.html로 이동)
        return "cms/regist";
    }




}
