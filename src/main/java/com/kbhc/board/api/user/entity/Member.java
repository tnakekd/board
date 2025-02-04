package com.kbhc.board.api.user.entity;

import com.kbhc.board.api.board.entity.Board;
import com.kbhc.board.api.board.entity.BoardComment;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.core.util.CommonUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "member")
    private List<Board> boards;

    @OneToMany(mappedBy = "member")
    private List<BoardComment> boardComments;

    public Member(UserRequest request) {
        name = request.getName();
        nickname = request.getNickname();
        email = request.getEmail();
        password = CommonUtil.encodePassword(request.getPassword());
        role = request.getRole();
    }

}
