package com.kbhc.board.api.user.dto;

import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.core.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCacheDTO {

    private String email;
    private String password;
    private Role role;

    public UserCacheDTO(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

}
