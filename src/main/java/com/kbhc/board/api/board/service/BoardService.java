package com.kbhc.board.api.board.service;

import com.kbhc.board.api.board.dto.BoardRequest;
import com.kbhc.board.api.board.dto.BoardResponse;
import com.kbhc.board.core.model.Response;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Response<Boolean> createBoard(BoardRequest request, MultipartFile file) throws Exception;
    Response<BoardResponse> findBoard(Long boardId) throws Exception;
    Response<BoardResponse> updateBoard(Long id, BoardRequest request, MultipartFile file) throws Exception;
    Response<Boolean> deleteBoard(Long id) throws Exception;

}
