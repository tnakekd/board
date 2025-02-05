package com.kbhc.board.api.board.service;

import com.kbhc.board.api.board.dto.CommentRequest;
import com.kbhc.board.core.model.Response;

public interface CommentService {

    Response<Boolean> createComment(CommentRequest request);

}
