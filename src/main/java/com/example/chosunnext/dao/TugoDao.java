package com.example.chosunnext.dao;

import com.example.chosunnext.dto.file.request.FileDto;
import com.example.chosunnext.dto.file.response.FileResponseDto;
import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.dto.tugo.response.TugoResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int countLike(int tugoId);

    TugoResponseDto getTugoById(int tugoId);

    List<FileResponseDto> getFileTugoById(int tugoId);

    int likeTugoStatus(@Param("tugoId") int tugoId, @Param("userId") String userId);

    void likeTugo(@Param("tugoId") int tugoId, @Param("userId") String userId);

    void deleteLike(@Param("tugoId") int tugoId, @Param("userId") String userId);

    List<TugoResponseDto> getTugoList(@Param("page") int page, @Param("size") int size, @Param("offset") int offset, @Param("sort") String sort);

    int getTotalBoardCount();

    void deleteTugo(int tugoId);

}
