package com.ssafy.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongCodeDto {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;

    public String concat() {
        return (sidoName == null ? "" : sidoName) + " " +
                (gugunName == null ? "" : gugunName) + " " +
                (dongName == null ? "" : dongName);
    }

}
