package com.ssafy.board.model.service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.CommentDto;

import java.util.List;


public interface BoardService {
    public int createBoard(BoardDto boardDto);

    public List<BoardDto> getBoardList(long aptCode);

    public int modifyBoard(BoardDto boardDto);

    public int deleteBoard(int bno);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int createComment(CommentDto commentDto);

    public List<CommentDto> getCommentList(int bno);

    public int modifyComment(CommentDto commentDto);

    public int deleteComment(int cno);

    public List<BoardDto> getNoticeBoardList();

}
