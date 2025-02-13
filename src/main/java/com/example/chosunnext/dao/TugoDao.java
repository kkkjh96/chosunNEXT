package com.example.chosunnext.dao;

import com.example.chosunnext.dto.file.request.FileDto;
import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : TugoDao
 * author         : 김재홍
 * date           : 25. 2. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 13.        김재홍       최초 생성
 */
@Mapper
public interface TugoDao {

    void registTugo(@Param("tugo") TugoRequestDto tugoRequestDto);

    int uploadsFile(FileDto file);

}
