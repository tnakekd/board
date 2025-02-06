package com.kbhc.board.api.board.controller;

import com.kbhc.board.api.board.dto.BoardRequest;
import com.kbhc.board.api.board.dto.BoardResponse;
import com.kbhc.board.api.board.service.BoardService;
import com.kbhc.board.core.model.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/board")
@Tag(name = "게시판 API", description = "게시글 관련 API")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Response<Boolean>> createBoard(
            @Parameter(description = "제목") @RequestParam("title") String title,
            @Parameter(description = "내용") @RequestParam("content") String content,
            @Parameter(description = "첨부파일") @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        BoardRequest request = new BoardRequest(title, content);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(boardService.createBoard(request, file, authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BoardResponse>> findBoard(
            @Parameter(description = "게시글 식별 ID", required = true) @PathVariable(name = "id") Long boardId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.findBoard(boardId));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Response<Boolean>> updateBoard(
            @Parameter(description = "게시글 식별 ID", required = true) @PathVariable(name = "id") Long boardId,
            @Parameter(description = "제목") @RequestParam("title") String title,
            @Parameter(description = "내용") @RequestParam("content") String content,
            @Parameter(description = "첨부파일") @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        BoardRequest request = new BoardRequest(title, content);
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, request, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deleteBoard(
            @Parameter(description = "게시글 식별 ID", required = true) @PathVariable(name = "id") Long boardId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(boardId));
    }

}
