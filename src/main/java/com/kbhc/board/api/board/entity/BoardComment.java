package com.kbhc.board.api.board.entity;

import com.kbhc.board.api.user.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member member;

}
