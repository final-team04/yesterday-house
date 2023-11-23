package com.ssafy.board.model.service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.CommentDto;
import com.ssafy.board.model.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoardServiceImpl implements BoardService{
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public int createBoard(BoardDto boardDto) {
        return boardMapper.createBoard(boardDto);
    }

    public List<BoardDto> getBoardList(long aptCode) {
        return boardMapper.getBoardList(aptCode);
    }

    public int modifyBoard(BoardDto boardDto) {
        return boardMapper.modifyBoard(boardDto);
    }

    public int deleteBoard(int bno) {
        return boardMapper.deleteBoard(bno);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int createComment(CommentDto commentDto) {
        return boardMapper.createComment(commentDto);
    }

    public List<CommentDto> getCommentList(int bno) {
        return boardMapper.getCommentList(bno);
    }

    public int modifyComment(CommentDto commentDto) {
        return boardMapper.modifyComment(commentDto);
    }

    public int deleteComment(int cno) {
        return boardMapper.deleteComment(cno);
    }

    public List<BoardDto> getNoticeBoardList() {
        return boardMapper.getNoticeBoardList();
    }
}
