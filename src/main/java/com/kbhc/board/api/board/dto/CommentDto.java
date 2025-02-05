package com.kbhc.board.api.board.dto;

import com.kbhc.board.api.board.entity.BoardComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    @Schema(description = "답변 내용")
    private String content;
    @Schema(description = "답변 작성자 메일")
    private String writerEmail;

    public CommentDto(BoardComment comment) {
        this.content = comment.getContent();
        this.writerEmail = comment.getMember().getEmail();
    }

}
