package com.example.chosunnext.dao;

import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.dto.mypage.response.MypageUserResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : MyPageDao
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
@Mapper
public interface MyPageDao {

    MypageMainResonseDto getUserActivity(String userId);

    MypageUserResponseDto getUserInfo(String userId);

    List<Map<String, Object>> getWeeklyViews(String userId);

}
