package com.example.chosunnext.dto.file.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : com.example.chosunnext.dto.file.request
 * fileName       : FileDto
 * author         : 김재홍
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        김재홍       최초 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

    private int fileId;
    private String fileGbnCd;
    private String fileRefId;
    private String fileName;
    private String fileUrl;
    private String instId;
    private String instDt;
    private String updtId;
    private String updtDt;

    private MultipartFile file;

}
