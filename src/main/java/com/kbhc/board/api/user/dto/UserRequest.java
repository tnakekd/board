package com.kbhc.board.api.user.dto;


import com.kbhc.board.core.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "UserCreateRequest", description = "회원 생성 정보 요청 값")
public class UserRequest {

    @Schema(description = "이름")
    private String name;
    @Schema(description = "닉네임")
    private String nickname;
    @NotNull(message = "email 은 필수입니다")
    @Schema(description = "이메일")
    private String email;
    @NotNull(message = "password 는 필수입니다")
    @Schema(description = "비밀번호")
    private String password;
    @NotNull(message = "role 은 필수입니다")
    @Schema(description = "권한")
    private Role role;

}
