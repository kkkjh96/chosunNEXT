package com.example.chosunnext.dao;

import com.example.chosunnext.dto.user.request.RequestUserDto;
import com.example.chosunnext.dto.user.response.ResponseUserDto;
import com.example.chosunnext.security.CustomUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : UserDao
 * author         : 김재홍
 * date           : 25. 1. 22.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 22.        김재홍       최초 생성
 */
@Mapper
public interface UserDao {

    ResponseUserDto getUserDto(@Param("userId") String user_id);

    int checkId(@Param("userId") String userId);

    void signup(RequestUserDto requestUserDto);

}
