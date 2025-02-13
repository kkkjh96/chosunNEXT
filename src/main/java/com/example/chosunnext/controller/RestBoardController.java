package com.example.chosunnext.controller;

import com.example.chosunnext.dto.tugo.request.TugoRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<String> registTugo(@RequestBody TugoRequestDto tugoRequestDto){
        log.info("tugoRequestDto : {}", tugoRequestDto);
        // TODO: TugoService.registTugo() 호출
        // TODO: 성공시 "게시�� 등록 성공" 반환, 실��시 "게시�� 등록 실��" 반환
        return ResponseEntity.ok().build();
    }

}
