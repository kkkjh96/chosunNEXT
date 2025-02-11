package com.example.chosunnext.controller;

import com.example.chosunnext.dto.file.request.FileDto;
import com.example.chosunnext.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestFileController
 * author         : 김재홍
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
public class RestFileController {

    private final FileUtils fileUtils;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("instId") String instId){

        log.info("AAAA" + file);
        log.info("BBBB" + instId);

        FileDto fileDto = new FileDto();

        fileDto.setFile(file);
        fileDto.setInstId(instId);
        fileDto.setFileGbnCd("400");

        String fileurl = fileUtils.uploadFile(fileDto);
        
        if(fileurl != null){
            return ResponseEntity.ok(fileurl);
        }
        
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestBody Map<String, String> request) {
        String fileUrl = request.get("fileUrl");

        // 파일 삭제 서비스 호출
        fileUtils.deleteFile(fileUrl);

        return ResponseEntity.ok().build();
    }
}
