package com.ssafy.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoardDto {

    private int bno;
    private String title;
    private String content;
    private int hit;
    private String date;
}
