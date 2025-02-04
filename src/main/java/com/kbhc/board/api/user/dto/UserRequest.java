package com.kbhc.board.api.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserCreateRequest", description = "회원 생성 정보 요청 값")
public class UserRequest {

    private String name;
    private String nickname;
    @NotNull(message = "email 은 필수입니다")
    private String email;
    @NotNull(message = "password 는 필수입니다")
    private String password;
    private String role;

}
