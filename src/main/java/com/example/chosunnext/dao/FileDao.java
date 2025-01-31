package com.example.chosunnext.dao;

import com.example.chosunnext.dto.file.request.FileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName    : com.example.chosunnext.dao
 * fileName       : FileDao
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@Mapper
public interface FileDao {

    int uploadsFile(FileDto file);

}
