package com.kbhc.board.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "email 은 필수입니다.")
    @Schema(description = "메일(Id)")
    private String email;

    @NotNull(message = "password 는 필수입니다.")
    @Schema(description = "비밀번호")
    private String password;

}
