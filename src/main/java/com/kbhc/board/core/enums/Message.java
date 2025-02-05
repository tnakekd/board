package com.kbhc.board.core.enums;

public enum Message {

    //User
    SUCCESS_USER_SAVE(1000, "회원 등록 성공"),
    SUCCESS_USER_LOGIN(1001, "로그인 성공"),

    FAILURE_USER_SAVE(2000, "회원 등록 실패"),
    FAILURE_USER_NOT_FOUND(2001, "존재하지 않는 회원"),
    FAILURE_USER_PASSWORD_MISMATCH(2002, "비밀번호 불일치"),
    FAILURE_USER_DUPLICATE_EMAIL(2003, "중복된 이메일입니다"),


    //Board
    SUCCESS_BOARD_SAVE(3000, "게시글 등록 성공"),
    SUCCESS_BOARD_SEARCH(3001, "게시글 조회 성공"),
    SUCCESS_BOARD_UPDATE(3002, "게시글 수정 성공"),
    SUCCESS_BOARD_DELETE(3003, "게시글 삭제 성공"),
    SUCCESS_BOARD_COMMENT_SAVE(3004, "답변 등록 성공"),


    FAILURE_BOARD_SAVE(4000, "게시글 등록 실패"),
    FAILURE_BOARD_NOT_FOUND(4001, "게시글이 존재하지 않음"),

    ERROR(-99, "시스템 오류");


    private final int code;
    private final String value;

    Message(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
