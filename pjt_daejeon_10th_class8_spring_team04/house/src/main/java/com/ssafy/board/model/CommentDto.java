package com.ssafy.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    public int cno;
    public int bno;
    public int uid ;
    public String content;
    public String date;
    public int parentCno;

}
