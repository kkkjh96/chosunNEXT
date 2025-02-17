package com.example.chosunnext.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.chosunnext.controller
 * fileName       : BoardController
 * author         : 김재홍
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        김재홍       최초 생성
 */
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    @GetMapping("/write")
    public String boardRegist() {
        log.info("BoardController.boardWrite()");
        return "/board/write";
    }

    @GetMapping("/detail/{tugoId}")
    public String boardDetail(@PathVariable("tugoId") int tugoId) {
        log.info("BoardController.boardDetail({})", tugoId);
        return "/board/detail";
    }

    @GetMapping("/list")
    public String boardList() {
        log.info("BoardController.boardList()");
        return "/board/list";
    }

}
