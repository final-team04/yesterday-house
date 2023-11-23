package com.ssafy.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private int bno;
    private long aptCode;
    private int uid;
    private String userId;
    private String userName;
    private String title;
    private String content;
    private int hit;
    private int like;
    private String date;
    private BoardType type;

}
