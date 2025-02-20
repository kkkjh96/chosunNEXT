package com.example.chosunnext.service;

import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : MypageService
 * author         : 김재홍
 * date           : 25. 2. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 20.        김재홍       최초 생성
 */
public interface MypageService {

    MypageMainResonseDto getUserActivity(String userId);

}
