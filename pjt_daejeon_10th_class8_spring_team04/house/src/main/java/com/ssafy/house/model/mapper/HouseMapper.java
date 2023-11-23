package com.ssafy.house.model.mapper;

import com.ssafy.house.model.CandidateDto;
import com.ssafy.house.model.DateRangeDto;
import com.ssafy.house.model.ResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HouseMapper {
    @Select("SELECT d.no, i.aptCode, i.apartmentName, d.dealYear, d.dealMonth, d.dealDay, d.floor, d.area, d.dealAmount, i.lng, i.lat, " +
            "i.buildYear, i.roadName, i.roadNameBonbun, i.roadNameBubun, i.dong, i.jibun, c.fullAddress " +
            "FROM housedeal AS d " +
            "INNER JOIN houseinfo AS i ON d.aptCode = i.aptCode " +
            "INNER JOIN dongcode AS c ON c.dongcode = i.dongCode " +
            "WHERE i.dongCode = #{dongCode} " +
            "AND DATE(CONCAT(d.dealYear, '-', d.dealMonth, '-', d.dealDay)) BETWEEN #{startDate} AND #{endDate} " +
            "AND i.apartmentName LIKE CONCAT('%', #{apartmentName}, '%') " +
            "ORDER BY DATE(CONCAT(d.dealYear, '-', d.dealMonth, '-', d.dealDay)) ASC")
    @ResultType(ResultDto.class)
    List<ResultDto> getAptByName(DateRangeDto dateRangeDto);

    @Select("SELECT d.no, i.aptCode, i.apartmentName, d.dealYear, d.dealMonth, d.dealDay, d.floor, d.area, d.dealAmount, i.lng, i.lat, " +
            "i.buildYear, i.roadName, i.roadNameBonbun, i.roadNameBubun, i.dong, i.jibun, c.fullAddress " +
            "FROM housedeal AS d " +
            "INNER JOIN houseinfo AS i ON d.aptCode = i.aptCode " +
            "INNER JOIN dongcode AS c ON c.dongcode = i.dongCode " +
            "WHERE i.dongCode = #{dongCode} " +
            "AND DATE(CONCAT(d.dealYear, '-', d.dealMonth, '-', d.dealDay)) BETWEEN #{startDate} AND #{endDate} " +
            "ORDER BY DATE(CONCAT(d.dealYear, '-', d.dealMonth, '-', d.dealDay)) ASC")
    @ResultType(ResultDto.class)
    List<ResultDto> getAptByDong(DateRangeDto dateRangeDto);

    @Select("SELECT dongCode, fullAddress " +
            "FROM dongcode " +
            "WHERE dongcode.fullAddress LIKE #{dongName}")
    List<CandidateDto> getDongNameCandidates(String dongName);
}
