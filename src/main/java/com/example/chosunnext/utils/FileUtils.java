package com.example.chosunnext.utils;

import com.example.chosunnext.dao.FileDao;
import com.example.chosunnext.dto.file.request.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component  // Spring의 컴포넌트로 등록하여 의존성 주입이 가능하도록 설정
@RequiredArgsConstructor  // Lombok을 사용하여 final 필드에 대한 생성자를 자동으로 생성
@Slf4j  // Lombok을 사용하여 로그를 위한 Logger 객체를 자동 생성
public class FileUtils {

    // 파일 정보를 데이터베이스에 저장하거나 조회하는 DAO 객체
    private final FileDao fileDao;

    // 프로젝트 루트 경로에 'uploads' 폴더를 생성하여 파일을 저장할 경로 설정
    private final String fileStoragePath = System.getProperty("user.dir") + "/uploads/";

    // 브라우저에서 업로드된 파일에 접근할 수 있는 URL 경로 설정
    private final String fileAccessUrl = "/uploads/";

    /**
     * 단일 파일 업로드 메서드
     *
     * @param fileDto 업로드할 파일 정보를 담고 있는 DTO 객체
     * @return 업로드된 파일의 URL 경로
     */
    public String uploadFile(FileDto fileDto) {
        // DTO에서 파일 객체를 가져옴
        MultipartFile file = fileDto.getFile();

        // 파일 유효성 검사 (크기 및 확장자 검증)
        validateFile(file);

        // 고유 파일명 생성 (원본 파일 이름에 타임스탬프를 추가)
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        // 파일이 저장될 전체 경로 설정
        String filePath = fileStoragePath + fileName;

        try {
            // 파일을 경로에 저장
            saveFile(file, filePath);

            // DTO에 저장된 파일 이름과 URL 설정
            fileDto.setFileName(fileName);
            fileDto.setFileUrl(fileAccessUrl + fileName);

            // 데이터베이스에 파일 정보를 저장
            int result = fileDao.uploadsFile(fileDto);

            // 데이터베이스에 파일 저장이 실패하면 예외를 발생시킴
            if (result == 0) {
                throw new RuntimeException("파일 저장 실패");
            }
        } catch (IOException e) {
            // 파일 저장 중 예외가 발생하면 로그를 출력하고 예외를 다시 던짐
            log.error("파일 업로드 실패: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }

        // 업로드된 파일의 URL 경로 반환
        return fileAccessUrl + fileName;
    }

    /**
     * 파일 저장 메서드
     *
     * @param file     저장할 파일 객체
     * @param filePath 저장할 파일의 경로
     * @throws IOException 파일 저장 중 오류가 발생하면 던지는 예외
     */
    private void saveFile(MultipartFile file, String filePath) throws IOException {
        // 파일 경로를 Path 객체로 변환
        Path path = Paths.get(filePath);

        // 파일이 저장될 폴더가 존재하지 않으면 생성
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        // 파일을 경로에 저장 (MultipartFile 메서드 사용)
        file.transferTo(path.toFile());
    }

    /**
     * 파일 유효성 검사 메서드
     *
     * @param file 검사할 파일 객체
     */
    private void validateFile(MultipartFile file) {
        // 파일 크기 제한 (5MB 초과 시 예외 발생)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("파일 크기가 10MB를 초과합니다.");
        }

        // 파일 이름을 가져옴
        String originalFileName = file.getOriginalFilename();

        // 허용된 확장자가 아니면 예외 발생
        if (originalFileName != null && !originalFileName.matches(".*\\.(png|jpg|jpeg|gif|pdf)$")) {
            throw new IllegalArgumentException("허용되지 않은 파일 형식입니다.");
        }
    }

    /**
     * 고유한 파일 이름 생성 메서드
     *
     * @param originalFileName 원본 파일 이름
     * @return 고유한 파일 이름 (타임스탬프가 추가된 파일 이름)
     */
    private String generateUniqueFileName(String originalFileName) {
        // 현재 시간을 기반으로 한 타임스탬프 생성
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());

        // 확장자 추출을 위한 변수 초기화
        String extension = "";

        // 원본 파일 이름에서 확장자를 추출
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        }

        // 고유 파일 이름 생성 (원본 이름 + 타임스탬프 + 확장자)
        return originalFileName + "_" + timestamp + extension;
    }
}
