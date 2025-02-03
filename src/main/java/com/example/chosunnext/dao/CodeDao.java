package com.example.chosunnext.dao;

import com.example.chosunnext.dto.category.response.ResponseCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : CodeDao
 * author         : 김재홍
 * date           : 25. 2. 3.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 3.        김재홍       최초 생성
 */
@Mapper
public interface CodeDao {

    List<ResponseCategoryDto> getCategories();

}
