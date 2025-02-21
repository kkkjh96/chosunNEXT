package com.example.chosunnext.dao;

import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.mypage.response.*;
import com.example.chosunnext.dto.user.request.UserRequestDto;
import com.example.chosunnext.dto.user.response.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    List<ArticleResponseDTO> getRecentViewed(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getRecentViewedCount(String userId);

    List<ArticleResponseDTO> getBookmarked(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getBookmarkedCount(String userId);

    List<ArticleResponseDTO> getDdabong(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getDdabongCount(String userId);

    List<ArticleResponseDTO> getTugo(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getTugoCount(String userId);

    List<MyCommentResponseDto> getCommented(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getCommentedCount(String userId);

    List<MySubscriptionResponseDto> getSubscribed(
            @Param("userId") String userId,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int getSubscribedCount(String userId);

    List<NewsDto> getSubscribedNews(String userId, int size, int offset);

    int getSubscribedNewsCount(String userId);

    UserResponseDto getUserAllInfo(String userId);

    int updateUserInfo(@Param("user") UserRequestDto requestDto);

    // ✅ 비밀번호 변경
    int updateUserPassword(@Param("user") UserRequestDto user);

    // ✅ 회원 탈퇴
    int deleteUserAccount(@Param("userId") String userId);

    List<OrdersResponseDto> getOrders(@Param("userId") String userId);

    CouponResponseDto getCouponByNum(String couponNum);

    void updateCouponStatus(String couponNum);

}
