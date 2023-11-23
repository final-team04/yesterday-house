package com.ssafy.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongFullNameDto {
    private String doName;
    private String siName;
    private String gugunName;
    private String eupmyeondongName;


    public boolean isEmpty() {
        return doName.isEmpty() &&
                siName.isEmpty() &&
                gugunName.isEmpty() &&
                eupmyeondongName.isEmpty();
    }

    public String concat() {
        return (doName == null ? "" : doName) + " " +
                (siName == null ? "" : siName) + " " +
                (gugunName == null ? "" : gugunName) + " " +
                (eupmyeondongName == null ? "" : eupmyeondongName);
    }
}
