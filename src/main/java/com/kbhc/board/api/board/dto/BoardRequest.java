package com.kbhc.board.api.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

}
