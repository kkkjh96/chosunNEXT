package com.example.chosunnext.service;

import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.dto.tugo.response.TugoResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : TugoService
 * author         : 김재홍
 * date           : 25. 2. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 13.        김재홍       최초 생성
 */
public interface TugoService {

    void registTugo(TugoRequestDto tugoRequestDto, List<MultipartFile> files);

    TugoResponseDto getTugoById(int tugoid);

    void likeTugo(int tugoId);
}
