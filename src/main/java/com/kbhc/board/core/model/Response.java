package com.kbhc.board.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

    @Schema(description = "성공 여부")
    private boolean success;
    @Schema(description = "결과 메시지")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;

    public static <T> Response<T> success(String message, T data) {
        Response<T> response = new Response<>();
        response.success = true;
        response.message = message;
        response.data = data;
        return response;
    }

    public static <T> Response<T> failure(String message) {
        Response<T> response = new Response<>();
        response.success = false;
        response.message = message;
        return response;
    }


}
