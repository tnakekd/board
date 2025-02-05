package com.kbhc.board.api.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {

    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @NotNull(message = "작성자 ID는 필수입니다.")
    @Schema(description = "작성자 식별 ID")
    private Long writer;

    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
