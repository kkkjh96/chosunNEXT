package com.example.chosunnext.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.chosunnext.dto
 * fileName       : NewsDto
 * author         : 박미정
 * date           : 25. 2. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 11.        박미정      최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private String user_id;
    private String title;
    private String sub_title;
    private String content;
    private String main_category_cd;
    private String sub_category_cd;
    private String reservation_time;
    private String news_type;
    private String writer;
    private LocalDateTime credate_dt;
    private LocalDateTime  update_dt;
    private String filename;

    public LocalDateTime getCredate_dt() {
        return credate_dt == null ? LocalDateTime.now() : credate_dt;
    }

    public LocalDateTime getUpdate_dt() {
        return update_dt == null ? LocalDateTime.now() : update_dt;
    }
}
