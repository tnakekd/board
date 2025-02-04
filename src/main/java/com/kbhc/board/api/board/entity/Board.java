package com.kbhc.board.api.board.entity;

import com.kbhc.board.api.user.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardComments;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member member;

}
