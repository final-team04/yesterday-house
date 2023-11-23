package com.ssafy.house.model.service;

import com.ssafy.house.model.CandidateDto;
import com.ssafy.house.model.DateRangeDto;
import com.ssafy.house.model.DongFullNameDto;
import com.ssafy.house.model.ResultDto;

import java.util.List;

public interface HouseService {

    List<ResultDto> getAptByName(DateRangeDto dateRangeDto);

    List<ResultDto> getAptByDong(DateRangeDto dateRangeDto);

    List<CandidateDto> getDongNameCandidates(DongFullNameDto dongFullName);
}
