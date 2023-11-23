package com.ssafy.house.controller;

import com.ssafy.house.model.CandidateDto;
import com.ssafy.house.model.DateRangeDto;
import com.ssafy.house.model.DongFullNameDto;
import com.ssafy.house.model.ResultDto;
import com.ssafy.house.model.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/house")
//@CrossOrigin("*")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    /* REST API server라서 page를 반환할 필요 없음

    @GetMapping("/")
    public ResponseEntity<?> getPage(){
        System.out.println("getPage");
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

     */
    @GetMapping("/apt")
    public ResponseEntity<?> getAptByName(String apartmentName, String dongCode, String startDate, String endDate) throws ParseException {
        DateRangeDto dateRangeDto = new DateRangeDto();
        dateRangeDto.setApartmentName(apartmentName);
        dateRangeDto.setDongCode(dongCode);
        dateRangeDto.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(startDate)));
        dateRangeDto.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(endDate)));

        List<ResultDto> result = houseService.getAptByName(dateRangeDto);

        if (!result.isEmpty()) {
            return new ResponseEntity<List<ResultDto>>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/dong")
    public ResponseEntity<?> getAptByDong(String dongCode, String startDate, String endDate) throws ParseException {
        DateRangeDto dateRangeDto = new DateRangeDto();
        dateRangeDto.setDongCode(dongCode);
        dateRangeDto.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(startDate)));
        dateRangeDto.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(endDate)));

        List<ResultDto> result = houseService.getAptByDong(dateRangeDto);

        if (!result.isEmpty()) {
            return new ResponseEntity<List<ResultDto>>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getKeywords(DongFullNameDto dongFullName) {

        if (dongFullName.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<CandidateDto> result = houseService.getDongNameCandidates(dongFullName);

        if (result.isEmpty())
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
