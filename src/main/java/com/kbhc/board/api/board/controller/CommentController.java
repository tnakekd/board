package com.kbhc.board.api.board.controller;

import com.kbhc.board.api.board.dto.CommentRequest;
import com.kbhc.board.api.board.service.CommentService;
import com.kbhc.board.core.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Response<Boolean>> createComment(
            @RequestBody CommentRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(commentService.createComment(request));
    }

}
