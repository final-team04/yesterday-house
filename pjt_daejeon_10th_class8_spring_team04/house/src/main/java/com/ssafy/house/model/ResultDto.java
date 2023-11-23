package com.ssafy.house.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private long no;
    private long aptCode;
    private String apartmentName;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String floor;
    private String area;
    private String dealAmount;
    private String lng;
    private String lat;
    private int buildYear;
    private String roadName;
    private int roadNameBonbun;
    private int roadNameBubun;
    private String dong;
    private String jibun;
    private String fullAddress;

}
