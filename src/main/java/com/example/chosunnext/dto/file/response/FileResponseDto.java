package com.example.chosunnext.dto.file.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.chosunnext.dto.file.response
 * fileName       : FileResponseDto
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
public class FileResponseDto {

    private String fileUrl;
    private String fileName;

}
