package com.ssafy.house.model.service;

import com.ssafy.house.model.CandidateDto;
import com.ssafy.house.model.DateRangeDto;
import com.ssafy.house.model.DongFullNameDto;
import com.ssafy.house.model.ResultDto;
import com.ssafy.house.model.mapper.HouseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    public HouseServiceImpl(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }

    @Override
    public List<ResultDto> getAptByName(DateRangeDto dateRangeDto) {
        return houseMapper.getAptByName(dateRangeDto);
    }

    @Override
    public List<ResultDto> getAptByDong(DateRangeDto dateRangeDto) {
        return houseMapper.getAptByDong(dateRangeDto);
    }

    @Override
    public List<CandidateDto> getDongNameCandidates(DongFullNameDto dongFullName) {
        StringBuilder dongName = new StringBuilder();
        String doName = dongFullName.getDoName();
        String siName = dongFullName.getSiName();
        String gugunName = dongFullName.getGugunName();
        String eupmyeondongName = dongFullName.getEupmyeondongName();

        if (!doName.isEmpty())
            dongName.append(doName, 0, 2)
                    .append("%도");
        if (!siName.isEmpty())
            dongName.append("%")
                    .append(siName, 0, 2)
                    .append("%시%");
        if (!gugunName.isEmpty())
            dongName.append("%")
                    .append(gugunName, 0, 2)
                    .append("%")
                    .append(gugunName.charAt(gugunName.length() - 1))
                    .append("%");
        if (!eupmyeondongName.isEmpty())
            dongName.append("%")
                    .append(eupmyeondongName, 0, 2)
                    .append("%")
                    .append(eupmyeondongName.charAt(eupmyeondongName.length() - 1));

        String concatenated = dongName.toString();
        System.out.println("concatenated = " + concatenated);

        return houseMapper.getDongNameCandidates(concatenated);
    }
}
