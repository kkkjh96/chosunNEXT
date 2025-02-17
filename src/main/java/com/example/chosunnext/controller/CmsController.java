package com.example.chosunnext.controller;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import com.example.chosunnext.dto.user.request.ReporterDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import com.example.chosunnext.security.CustomUserDetails;
import com.example.chosunnext.service.CategoryService;
import com.example.chosunnext.service.NewsService;
import com.example.chosunnext.service.ReporterService;
import com.example.chosunnext.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;



    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model, HttpSession session) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        if (session.getAttribute("profileImage") == null) {
            String profileImageUrl = userService.getUserProfileImage(userDetails.getUsername());
            session.setAttribute("profileImage", profileImageUrl);
        }

        model.addAttribute("profileImage", session.getAttribute("profileImage"));

        return "/cms/main";
    }



    @GetMapping("/regist")
    public String cmsRegistPage(Model model) {
        List<ResponseCategoryDto> categories = categoryService.getCategories();
        List<ResponseReporterDto> reporters = reporterService.getReporter();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String categoriesJson = objectMapper.writeValueAsString(categories);
            model.addAttribute("categoriesJson", categoriesJson);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("categoriesJson", "[]");
        }

        model.addAttribute("categories", categories);
        model.addAttribute("reporters", reporters);

        return "/cms/regist";
    }


    @GetMapping("/news_List")
    public String cmsListPage() {
        return "cms/news_list";
    }


    @GetMapping("/update/{newsId}")
    public String cmsUpdatePage(@PathVariable("newsId") Long newsId, Model model) {
        NewsDto news = newsService.getNewsById(newsId);

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();
        List<ResponseReporterDto> reporters = reporterService.getReporter();

        model.addAttribute("news", news);
        model.addAttribute("categories", responseCategories);
        model.addAttribute("reporters", reporters);
        return "cms/update";
    }

}
