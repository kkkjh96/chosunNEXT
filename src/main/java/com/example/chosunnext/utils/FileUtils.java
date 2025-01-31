package com.example.chosunnext.utils;

import com.example.chosunnext.dao.FileDao;
import com.example.chosunnext.dto.file.request.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * packageName    : com.example.chosunnext.utils
 * fileName       : FileUtils
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtils {

    private final FileDao fileDao;

    private final String fileStoragePath = "/static/uploads/";

    public int uploadFile(FileDto file){
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());

        MultipartFile mFile = file.getFile();

        // 파일 확장자 추출
        String originalFileName = mFile.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 파일 이름에 현재 시간 추가
        String fileName = originalFileName.replace(extension, "") + "_" + timestamp + extension;

        // 파일 경로 설정
        String filePath = fileStoragePath + fileName;

        try {
            // 디렉토리 생성 및 파일 저장
            Files.createDirectories(Paths.get(fileStoragePath));
            mFile.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }

        // 저장된 파일 정보 설정
        file.setFileName(fileName);
        file.setFileUrl(filePath);

        int result = fileDao.uploadsFile(file);

        if(result == 0){
            throw new RuntimeException("파일 저장 실패");
        }

        return result;
    }

}
