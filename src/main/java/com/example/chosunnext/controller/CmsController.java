package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import com.example.chosunnext.dto.user.request.ReporterDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import com.example.chosunnext.service.CategoryService;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.ReporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cms")
public class CmsController {

    private final CategoryService categoryService;
    private final ReporterService reporterService;
    private final NewsService newsService;

    @GetMapping("/")
    public String cms() {
        return "/cms/main";
    }

    @GetMapping("/regist")
    public String cmsRegistPage(Model model) {

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();
        List<ResponseReporterDto>  reporters = reporterService.getReporter();

        model.addAttribute("categories", responseCategories);
        model.addAttribute("reporters", reporters);

        return "cms/regist";
    }

    @GetMapping("/news_List")
    public String cmsListPage() {
        return "cms/news_list";
    }

    @GetMapping("/update/{newsId}")
    public String cmsUpdatePage(@PathVariable("newsId") Long newsId, Model model) {
        NewsDto news = newsService.getNewsById(newsId);
        if (news == null) {
            return "error";  // 뉴스가 없으면 에러 페이지로 이동
        }

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();
        List<ResponseReporterDto> reporters = reporterService.getReporter();

        model.addAttribute("news", news);
        model.addAttribute("categories", responseCategories);
        model.addAttribute("reporters", reporters);
        return "cms/update";
    }

}
