package com.example.chosunnext.service;

import com.example.chosunnext.dto.mypage.response.LibraryResponseDto;
import com.example.chosunnext.dto.mypage.response.MypageMainResonseDto;
import com.example.chosunnext.dto.mypage.response.SubscribedNewsResponseDto;
import com.example.chosunnext.dto.user.request.UserCheckRequestDto;
import com.example.chosunnext.dto.user.response.UserResponseDto;

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

    <T> LibraryResponseDto<T> getLibraryContent(String tab, int page, int size, String userId);

    SubscribedNewsResponseDto getSubscribedNews(String userId, int page, int size);

    UserResponseDto getUserInfo(UserCheckRequestDto userCheckRequestDto);
}
