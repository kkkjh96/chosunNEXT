package com.example.chosunnext.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

@Controller
@RequestMapping("/categoryNews")
public class CategoryNewsController {
d
    @GetMapping("/{category}")
    public String categoryPage(@PathVariable String category) {
        return "categoryNews/mainCategory" ;
    }

    @GetMapping("/detailNews/{newsId}")
    public String detailNewsPage(@PathVariable("newsId") String newsId, Model model, @RequestParam("category") String category) {
        model.addAttribute("newsId", newsId);
        model.addAttribute("category", category);
        return "/categoryNews/detailNews";
    }

    @GetMapping("/{category}/{subCategory}")
    public String categoryPageSub(
            @PathVariable("category") String category,
            @PathVariable(value = "subCategory", required = false) String subCategory) {

        if (subCategory == null || subCategory.isEmpty()) {
            return "categoryNews/mainCategory"; // 서브 카테고리가 없으면 상위 카테고리 페이지로 리다이렉트
        }

        return "categoryNews/subCategory" ;

    }


}