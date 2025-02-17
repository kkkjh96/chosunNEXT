package com.example.chosunnext.controller;

import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.dto.tugo.response.TugoResponseDto;
import com.example.chosunnext.security.CustomUserDetails;
import com.example.chosunnext.service.TugoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
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
            @RequestPart(value = "files", required = false)  List<MultipartFile> files
    ) {
        log.info("tugoRequestDto : {}", tugoRequestDto);
        log.info("files : {}", files);

        if (files == null) {
            files = Collections.emptyList();
        }

        tugoService.registTugo(tugoRequestDto, files);

        return ResponseEntity.ok().build();
    }


    /**
     * 게시글 상세보기 API
     */
    @GetMapping("/{tugoId}")
    public ResponseEntity<TugoResponseDto> getTugoById(@PathVariable("tugoId") int tugoId) {
        log.info("📌 상세 조회 요청 - tugoId : {}", tugoId);

        TugoResponseDto responseDto = tugoService.getTugoById(tugoId);
        log.info("상세 조회 - responseDto : {}", responseDto);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{tugoId}/like")
    public ResponseEntity<String> likeTugo(@PathVariable("tugoId") int tugoId, HttpSession session) {

        CustomUserDetails authentication = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication();

        log.info("유저 정보1:{}", authentication.getUsername());

        log.info("�� 좋아요 - tugoId : {}", tugoId);

        tugoService.likeTugo(tugoId);

        return ResponseEntity.ok().build();
    }

}
