package com.kbhc.board.api.board.controller;

import com.kbhc.board.api.board.dto.BoardRequest;
import com.kbhc.board.api.board.dto.BoardResponse;
import com.kbhc.board.api.board.service.BoardService;
import com.kbhc.board.core.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Response<Boolean>> createBoard(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("writer") Long writer,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        BoardRequest request = new BoardRequest(title, content, writer);
        return ResponseEntity.status(HttpStatus.OK).body(boardService.createBoard(request, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BoardResponse>> findBoard(
            @PathVariable(name = "id") Long boardId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findBoard(boardId));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Response<BoardResponse>> updateBoard(
            @PathVariable(name = "id") Long boardId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        BoardRequest request = new BoardRequest(title, content);
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, request, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deleteBoard(
            @PathVariable(name = "id") Long boardId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(boardId));
    }

}
