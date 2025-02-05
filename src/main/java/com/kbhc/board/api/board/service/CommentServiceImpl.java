package com.kbhc.board.api.board.service;

import com.kbhc.board.api.board.dto.CommentRequest;
import com.kbhc.board.api.board.entity.Board;
import com.kbhc.board.api.board.entity.BoardComment;
import com.kbhc.board.api.board.repository.BoardRepository;
import com.kbhc.board.api.board.repository.CommentRepository;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.repository.MemberRepository;
import com.kbhc.board.core.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.kbhc.board.core.enums.Message.*;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public CommentServiceImpl(CommentRepository commentRepository
            , MemberRepository memberRepository
            , BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    @CacheEvict(value = "board", key = "#request.boardId")
    public Response<Boolean> createComment(CommentRequest request, String createComment) {

        BoardComment boardComment = new BoardComment(request);

        Member member = memberRepository.findByEmail(createComment);

        if (Objects.isNull(member)) {
            return Response.failure(FAILURE_USER_NOT_FOUND.getValue());
        }

        Board board = boardRepository.findById(request.getBoardId()).orElse(null);

        if (Objects.isNull(board)) {
            return Response.failure(FAILURE_BOARD_NOT_FOUND.getValue());
        }

        boardComment.setMember(member);
        boardComment.setBoard(board);

        commentRepository.save(boardComment);

        return Response.success(SUCCESS_BOARD_COMMENT_SAVE.getValue(), true);
    }

}
