package com.example.chosunnext.service;

import com.example.chosunnext.dto.user.request.RequestUserDto;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : UserService
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
public interface UserService {

    boolean checkId(String userId);

    void signup(RequestUserDto requestUserDto);

    ResponseUserDto getUser(String userId);

    String getUserProfileImage(@Param("userId") String userId);
}
