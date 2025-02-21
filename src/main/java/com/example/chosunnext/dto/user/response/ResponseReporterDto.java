package com.example.chosunnext.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.chosunnext.dto.user.response
 * fileName       : ResponseReporterDto
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
public class ResponseReporterDto {
    private String userId;        // user_id
    private String name;          // 기자 이름
    private String role;          // 역할 (reporter)
    private String department;
    private String writer;
    private String fileName;
    private String filePath;
}