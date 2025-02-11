package com.example.chosunnext.dao;

import com.example.chosunnext.dto.user.request.ReporterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReporterDao {
    List<ReporterDto> getReporterDto();
}
