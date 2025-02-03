package com.example.chosunnext.service;

import com.example.chosunnext.dao.CodeDao;
import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : CatogoryServiceImpl
 * author         : 김재홍
 * date           : 25. 2. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 3.        김재홍       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CodeDao codeDao;

    @Override
    public List<ResponseCategoryDto> getCategories() {

        List<ResponseCategoryDto> categories = codeDao.getCategories();

        return categories;
    }
}
