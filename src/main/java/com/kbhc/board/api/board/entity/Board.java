package com.kbhc.board.api.board.entity;

import com.kbhc.board.api.board.dto.BoardRequest;
import com.kbhc.board.api.user.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @Column(name = "mod_date")
    private LocalDateTime modDate;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<BoardComment> boardComments;

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member member;

    @PrePersist
    public void prePersist() {
        this.regDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modDate = LocalDateTime.now();
    }

    public Board(BoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
