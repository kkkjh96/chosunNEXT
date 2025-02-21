package com.example.chosunnext.service;

import com.example.chosunnext.dao.MyPageDao;
import com.example.chosunnext.dto.NewsDto;
import com.example.chosunnext.dto.mypage.response.*;
import com.example.chosunnext.dto.user.request.UserCheckRequestDto;
import com.example.chosunnext.dto.user.request.UserRequestDto;
import com.example.chosunnext.dto.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : MypageServiceImpl
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MypageServiceImpl implements MypageService {

    private final MyPageDao myPageDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MypageMainResonseDto getUserActivity(String userId) {

        MypageMainResonseDto res = myPageDao.getUserActivity(userId);

        MypageUserResponseDto user = myPageDao.getUserInfo(userId);

        log.info("메인페이지 User : " + user);

        if(user.getNickname() == null){
            res.setUsername(user.getUserId());
        } else {
            res.setUsername(user.getNickname());
        }
        res.setPoint(user.getPoint());

        List<Map<String, Object>> weeklyViewData = myPageDao.getWeeklyViews(userId);

        // 최근 7일 날짜 초기화 (데이터가 없는 날짜는 0으로 설정)
        Map<String, Integer> weeklyViews = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            String date = today.minusDays(i).toString();
            weeklyViews.put(date, 0);
        }

        // 조회 데이터 반영
        for (Map<String, Object> data : weeklyViewData) {
            String day = (String) data.get("day");
            weeklyViews.put(day, ((Long) data.get("count")).intValue());
        }

        res.setWeeklyViews(weeklyViews);

        return res;
    }

    @Override
    public <T> LibraryResponseDto<T> getLibraryContent(String tab, int page, int size, String userId) {
        int offset = (page - 1) * size;
        int totalCount;
        List<T> data;

        switch (tab) {
            case "recent":
                data = (List<T>) myPageDao.getRecentViewed(userId, size, offset);
                totalCount = myPageDao.getRecentViewedCount(userId);
                break;
            case "bookmarked":
                data = (List<T>) myPageDao.getBookmarked(userId, size, offset);
                totalCount = myPageDao.getBookmarkedCount(userId);
                break;
            case "ddabong":
                data = (List<T>) myPageDao.getDdabong(userId, size, offset);
                totalCount = myPageDao.getDdabongCount(userId);
                break;
            case "tugo":
                data = (List<T>) myPageDao.getTugo(userId, size, offset);
                totalCount = myPageDao.getTugoCount(userId);
                break;
            case "commented":
                data = (List<T>) myPageDao.getCommented(userId, size, offset);
                totalCount = myPageDao.getCommentedCount(userId);
                break;
            case "subscribed":
                data = (List<T>) myPageDao.getSubscribed(userId, size, offset);
                totalCount = myPageDao.getSubscribedCount(userId);
                break;
            default:
                return new LibraryResponseDto<>(0, 0, List.of());
        }

        int totalPages = (int) Math.ceil((double) totalCount / size);
        return new LibraryResponseDto<>(totalCount, totalPages, data);
    }

    @Override
    public SubscribedNewsResponseDto getSubscribedNews(String userId, int page, int size) {

        int offset = (page - 1) * size;
        List<NewsDto> newsList = myPageDao.getSubscribedNews(userId, size, offset);
        int totalCount = myPageDao.getSubscribedNewsCount(userId);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new SubscribedNewsResponseDto(newsList, totalCount, totalPages);
    }

    @Override
    public UserResponseDto getUserInfo(UserCheckRequestDto userCheckRequestDto) {

        UserResponseDto user = myPageDao.getUserAllInfo(userCheckRequestDto.getUserId());

        if(user == null || !passwordEncoder.matches(userCheckRequestDto.getPassword(), user.getPassword())){
            return null;
        }
        
        return user;
    }

    @Override
    public int updateUserInfo(UserRequestDto userRequestDto) {

        return myPageDao.updateUserInfo(userRequestDto);
    }

    @Override
    public int updatePassword(UserRequestDto user) {

        user.setNewPassword(passwordEncoder.encode(user.getNewPassword()));

        return myPageDao.updateUserPassword(user);
    }

    @Override
    public int deleteUserAccount(UserRequestDto user) {

        UserResponseDto userResponseDto = myPageDao.getUserAllInfo(user.getUserId());

        log.info("Deleting user:{}", userResponseDto);

        if(userResponseDto == null || !passwordEncoder.matches(user.getPassword(), userResponseDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return myPageDao.deleteUserAccount(user.getUserId());
    }

    @Override
    public List<OrdersResponseDto> getOrders(String userId) {

        return myPageDao.getOrders(userId);
    }

    @Override
    public String registerCoupon(String couponNum) {

        CouponResponseDto coupon = myPageDao.getCouponByNum(couponNum);

        if(coupon == null){
            return "존재하지 않는 쿠폰입니다.";
        }
        
        if("Y".equals(coupon.getCouponStatus())){
            return "이미 사용된 쿠폰입니다.";
        }

        myPageDao.updateCouponStatus(couponNum);

        return "쿠폰이 정상적으로 등록되었습니다.";
    }
}
