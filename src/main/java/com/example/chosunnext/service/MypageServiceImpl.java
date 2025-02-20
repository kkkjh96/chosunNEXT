package com.example.chosunnext.service;

import com.example.chosunnext.dao.MyPageDao;
import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.dto.mypage.response.MypageUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
