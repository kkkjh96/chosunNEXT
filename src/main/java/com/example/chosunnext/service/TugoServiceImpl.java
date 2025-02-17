package com.example.chosunnext.service;

import com.example.chosunnext.dao.TugoDao;
import com.example.chosunnext.dto.file.request.FileDto;
import com.example.chosunnext.dto.file.response.FileResponseDto;
import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.dto.tugo.response.TugoResponseDto;
import com.example.chosunnext.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.service
 * fileName       : TugoServiceImpl
 * author         : 김재홍
 * date           : 25. 2. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 13.        김재홍       최초 생성
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TugoServiceImpl implements TugoService {

    private final FileUtils fileUtils;
    private final TugoDao tugoDao;

    @Override
    @Transactional
    public void registTugo(TugoRequestDto tugoRequestDto, List<MultipartFile> files) {

        tugoDao.registTugo(tugoRequestDto);

        if (files != null && !files.isEmpty()) {
            for(MultipartFile file : files){
                FileDto dto = new FileDto();
                dto.setTugoId(tugoRequestDto.getTugoId());
                dto.setFile(file);
                dto.setInstId(tugoRequestDto.getUserId());
                dto.setFileGbnCd("400");

                fileUtils.uploadFileTUgo(dto);
            }
        }

    }

    @Override
    @Transactional
    public TugoResponseDto getTugoById(int tugoId) {

        TugoResponseDto tugoResponseDto = tugoDao.getTugoById(tugoId);
        tugoResponseDto.setLikeCount(tugoDao.countLike(tugoId));

        List<FileResponseDto> fileResponseDtoList;

        fileResponseDtoList = tugoDao.getFileTugoById(tugoId);

        log.info("file : {}", fileResponseDtoList);

        tugoResponseDto.setFiles(fileResponseDtoList);

        return tugoResponseDto;

    }

    @Override
    public void likeTugo(int tugoId, String userId) {
        tugoDao.likeTugo(tugoId, userId);
    }

    @Override
    public boolean likeTugoStatus(int tugoId, String userId) {

        int result = tugoDao.likeTugoStatus(tugoId, userId);

        boolean status;

        status = result > 0;

        return status;
    }

    @Override
    public void deleteLike(int tugoId, String userId) {
        tugoDao.deleteLike(tugoId, userId);
    }

    @Override
    public List<TugoResponseDto> getTugoList(int page, int size, int offset, String sort) {
        return tugoDao.getTugoList(page, size, offset, sort);
    }

    @Override
    public int getTotalBoardCount() {
        return tugoDao.getTotalBoardCount();
    }

    @Override
    public void deleteTugo(int tugoId) {
        tugoDao.deleteTugo(tugoId);
    }

}
