package com.kbhc.board.api.board.entity;

import com.kbhc.board.api.board.dto.CommentRequest;
import com.kbhc.board.api.user.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    public BoardComment(CommentRequest request) {
        this.content = request.getContent();
    }

}
