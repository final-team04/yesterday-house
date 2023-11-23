package com.ssafy.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeDto {

    private String dongCode;
    private String apartmentName;
    private String startDate;
    private String endDate;

}
