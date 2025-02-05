package com.kbhc.board.api.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "답변 요청")
public class CommentRequest {

    @Schema(description = "게시글(원글) 식별 ID")
    private Long boardId;
    @Schema(description = "답변 내용")
    private String content;

}
