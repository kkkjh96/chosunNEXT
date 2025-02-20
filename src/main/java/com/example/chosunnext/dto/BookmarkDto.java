package com.example.chosunnext.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkDto {
    private int bookmarkId;
    private int newsId;
    private String userId;
    private Date bookmarkDate;
    private int bookmarked;

}
