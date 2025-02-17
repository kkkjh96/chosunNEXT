package com.example.chosunnext.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private int newsId;
    private String userId;
    private String title;
    private String subTitle;
    private String content;

    private String mainCategoryCd;
    private String subCategoryCd;
    private LocalDateTime reservationTime;
    private String newsType;
    private String writer;
    private LocalDateTime credateDt;
    private LocalDateTime updateDt;
    private String fileName;


    public LocalDateTime getCredate_dt() {
        return credateDt == null ? LocalDateTime.now() : credateDt;
    }

    public LocalDateTime getUpdate_dt() {
        return updateDt == null ? LocalDateTime.now() : updateDt;
    }


}
