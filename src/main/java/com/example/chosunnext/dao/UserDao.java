package com.example.chosunnext.dao;

import com.example.chosunnext.dto.user.response.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jmx.export.annotation.ManagedNotification;

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

    UserDto getUserDto(@Param("userId") String user_id);

}
