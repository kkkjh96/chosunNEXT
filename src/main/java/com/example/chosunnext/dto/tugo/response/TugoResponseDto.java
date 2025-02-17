package com.example.chosunnext.dto.tugo.response;

import com.example.chosunnext.dto.file.response.FileResponseDto;
import lombok.*;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.tugo.response
 * fileName       : TugoResponseDto
 * author         : 김재홍
 * date           : 25. 2. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 13.        김재홍       최초 생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TugoResponseDto {

    private String tugoId;
    private String userId;
    private String title;
    private String contents;
    private String createDt;

    private int likeCount;
    private int dislike;

    private List<FileResponseDto> files;

}
