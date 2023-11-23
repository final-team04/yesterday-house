package com.ssafy.board.controller;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.CommentDto;
import com.ssafy.board.model.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
//@CrossOrigin("*")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardDto boardDto) {
        int result = boardService.createBoard(boardDto);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBoardList(long aptCode) {
        List<BoardDto> result = boardService.getBoardList(aptCode);
        if (result != null && !result.isEmpty()) {
            return new ResponseEntity<List<BoardDto>>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/notice")
    public ResponseEntity<?> getNoticeBoardList() {
        List<BoardDto> result = boardService.getNoticeBoardList();
        if (result == null || result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modifyBoard(@RequestBody BoardDto boardDto) {
        int result = boardService.modifyBoard(boardDto);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{bno}")
    public ResponseEntity<?> deleteBoard(@PathVariable int bno) {
        int result = boardService.deleteBoard(bno);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        int result = boardService.createComment(commentDto);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/comment/{bno}")
    public ResponseEntity<?> getCommentList(@PathVariable int bno) {
        List<CommentDto> result = boardService.getCommentList(bno);
        if (result != null && !result.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody CommentDto commentDto) {
        int result = boardService.modifyComment(commentDto);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/comment/{cno}")
    public ResponseEntity<?> deleteComent(@PathVariable int cno) {
        int result = boardService.deleteComment(cno);
        if (result != -1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

}
