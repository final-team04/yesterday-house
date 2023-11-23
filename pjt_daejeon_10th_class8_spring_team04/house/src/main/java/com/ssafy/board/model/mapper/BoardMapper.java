package com.ssafy.board.model.mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // board
    @Insert("INSERT INTO board (aptCode, uid, title, content) VALUES (#{aptCode}, (SELECT uid FROM user WHERE userId = #{userId}), #{title}, #{content})")
    int createBoard(BoardDto boardDto);

    @Select("select b.bno, b.aptCode, b.uid, u.userId, u.userName,  b.title, b.content, b.hit, b.`like`, b.date from board as b inner join user as u on b.uid = u.uid where aptCode = #{aptCode}")
    public List<BoardDto> getBoardList(long aptCode);

    @Update("UPDATE board SET content = #{content} WHERE bno = #{bno}")
    public int modifyBoard(BoardDto boardDto);

    @Delete("DELETE FROM board WHERE bno = #{bno}")
    public int deleteBoard(int bno);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // notice board
    @Select("select bno, title, content, hit, date from board where type='NOTICE' order by date desc limit 5")
    List<BoardDto> getNoticeBoardList();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // comment
    @Insert("INSERT INTO comment (bno, uid, content) VALUES (#{bno}, (SELECT uid FROM user WHERE userId = #{userId}), #{content})")
    int createComment(CommentDto commentDto);

    @Select("select cno, bno, uid, content, date, parentCno from comment where bno = #{bno}")
    public List<CommentDto> getCommentList(int bno);

    @Update("UPDATE comment SET content= #{content} WHERE cno = #{cno}")
    public int modifyComment(CommentDto commentDto);

    @Delete("DELETE FROM comment WHERE cno = #{cno};")
    public int deleteComment(int cno);

}
