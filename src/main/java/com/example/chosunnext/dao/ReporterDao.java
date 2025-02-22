package com.example.chosunnext.dao;

import com.example.chosunnext.dto.SubscriptionDto;
import com.example.chosunnext.dto.user.request.ReporterDto;
import com.example.chosunnext.dto.user.response.ResponseReporterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReporterDao {
    List<ResponseReporterDto> getReporterDto();
    List<ResponseReporterDto>  fetchReporterDetailsWithDepartment(@Param("newsId") int newsId);


}
