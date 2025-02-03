package com.example.chosunnext.controller;

import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import com.example.chosunnext.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestCommonController
 * author         : 김재홍
 * date           : 25. 2. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 3.        김재홍       최초 생성
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class RestCommonController {

    private final CategoryService categoryService;

    @GetMapping("/api/footer/categories")
    public ResponseEntity<List<ResponseCategoryDto>> getFooterCategories() {

        List<ResponseCategoryDto> responseCategories = categoryService.getCategories();

        log.info(responseCategories.toString());

        return ResponseEntity.ok(responseCategories);
    }

}
