package com.kbhc.board.api.board.dto;

import com.kbhc.board.api.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
public class BoardResponse {

    @Schema(description = "식별자")
    private Long id;
    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String content;
    @Schema(description = "첨부 파일 경로 + 파일명")
    private String file;
    @Schema(description = "작성자 email")
    private String writerEmail;
    @Schema(description = "등록일")
    private String regDate;
    @Schema(description = "수정일")
    private String modDate;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        if (Objects.nonNull(board.getFileName()) && !board.getFileName().isBlank()) {
            this.file = String.join("\\", board.getFilePath(), board.getFileName());
        }
        this.writerEmail = board.getMember().getEmail();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.regDate = board.getRegDate().format(formatter);
        if (Objects.nonNull(board.getModDate())) {
            this.modDate = board.getModDate().format(formatter);
        }
    }

}
