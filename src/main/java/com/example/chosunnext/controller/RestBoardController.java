package com.example.chosunnext.controller;

import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import com.example.chosunnext.dto.tugo.response.TugoResponseDto;
import com.example.chosunnext.service.TugoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> likeTugo(@PathVariable("tugoId") int tugoId,  Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String userId = authentication.getName();

        tugoService.likeTugo(tugoId, userId);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{tugoId}/like")
    public ResponseEntity<String> deleteLike(@PathVariable("tugoId") int tugoId,  Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String userId = authentication.getName();

        tugoService.deleteLike(tugoId, userId);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{tugoId}/like-status")
    public ResponseEntity<Map<String, Boolean>> getLikeStatus(@PathVariable("tugoId") int tugoId, Authentication authentication) {

        Map<String, Boolean> response = new HashMap<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("likedByUser", false);
            return ResponseEntity.ok(response);
        }

        String userId = authentication.getName();

        boolean likedByUser = tugoService.likeTugoStatus(tugoId, userId); // 🔹

        response.put("likedByUser", likedByUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getTugoList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sort) {

        int offset = (page-1) * size;

        Map<String, Object> response = new HashMap<>();
        List<TugoResponseDto> posts = tugoService.getTugoList(page, size, offset, sort);
        log.info("getBoardList - response : {}", posts);
        int totalCount = tugoService.getTotalBoardCount();

        response.put("posts", posts);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok(response);
    }

}
