package com.example.chosunnext.service;

import com.example.chosunnext.dao.UserDao;
import com.example.chosunnext.dto.user.request.RequestUserDto;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : UserServiceImpl
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkId(String userId) {

        return userDao.checkId(userId) > 0;

    }

    @Override
    public void signup(RequestUserDto requestUserDto) {

        String pw = passwordEncoder.encode(requestUserDto.getPassword());
        requestUserDto.setPassword(pw);

        userDao.signup(requestUserDto);

    }

    @Override
    public ResponseUserDto getUser(String userId) {

        ResponseUserDto userDto = userDao.getUserDto(userId);

        if (userDto == null){
            throw new IllegalArgumentException("해당 ID의 유저가 존재하지 않습니다.");
        }

        return userDto;

    }


    public String getUserProfileImage(String userId) {
        String profileImage = userDao.findProfileImageByUserId(userId);
        return (profileImage != null) ? profileImage : "/images/default.png"; // 기본 이미지 반환
    }
}
