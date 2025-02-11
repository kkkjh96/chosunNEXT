package com.example.chosunnext.service;

import com.example.chosunnext.dao.CodeDao;
import com.example.chosunnext.dao.ReporterDao;
import com.example.chosunnext.dto.user.request.ReporterDto;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporterService {

    private final ReporterDao reporterDao;

    public List<ReporterDto> getReporter() {
        List<ReporterDto> reporters = reporterDao.getReporterDto();
        System.out.println(reporters);
        return reporters;
    }
}
