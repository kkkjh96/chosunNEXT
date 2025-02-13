package com.example.chosunnext.controller;

import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.service.TugoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : RestBoardController
 * author         : 김재홍
 * date           : 25. 2. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 12.        김재홍       최초 생성
 */
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class RestBoardController {

    private final TugoService tugoService;

    @PostMapping
    public ResponseEntity<String> registTugo(
            @RequestPart("data") TugoRequestDto tugoRequestDto,
            @RequestPart("files") List<MultipartFile> files
    ) {
        log.info("tugoRequestDto : {}", tugoRequestDto);
        log.info("files : {}", files);

        tugoService.registTugo(tugoRequestDto, files);

        return ResponseEntity.ok().build();
    }

}
