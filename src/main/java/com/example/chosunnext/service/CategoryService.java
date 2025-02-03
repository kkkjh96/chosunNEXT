package com.example.chosunnext.service;

import com.example.chosunnext.dto.category.response.ResponseCategoryDto;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : CategoryService
 * author         : 김재홍
 * date           : 25. 2. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 3.        김재홍       최초 생성
 */
public interface CategoryService {

    List<ResponseCategoryDto> getCategories();

}
