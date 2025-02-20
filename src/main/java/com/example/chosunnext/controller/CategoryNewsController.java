package com.example.chosunnext.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;

@Controller
@RequestMapping("/categoryNews")
public class CategoryNewsController {

    @GetMapping("/{category}")
    public String categoryPage(@PathVariable String category) {
        return "categoryNews/" + category;
    }

    @GetMapping("/detailNews/{newsId}")
    public String detailNewsPage(@PathVariable("newsId") String newsId, Model model) {
        model.addAttribute("newsId", newsId);
        return "/categoryNews/detailNews";
    }


}