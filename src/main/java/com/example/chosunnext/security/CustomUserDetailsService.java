package com.example.chosunnext.security;

import com.example.chosunnext.dao.UserDao;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : org.example.chosunnext.security
 * fileName       : CustomUserDetailsService
 * author         : 김재홍
 * date           : 2025-01-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        김재홍       최초 생성
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseUserDto userDto = userDao.getUserDto(username);
        if (userDto == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        return new CustomUserDetails(userDto);
    }
}