package com.kbhc.board.core.enums;

public enum Message {

    //User
    SUCCESS_USER_SAVE(100, "회원 등록 성공"),
    SUCCESS_USER_LOGIN(101, "로그인 성공"),

    FAILURE_USER_SAVE(300, "회원 등록 실패"),
    FAILURE_USER_NOT_FOUND(301, "존재하지 않는 회원"),
    FAILURE_USER_PASSWORD_MISMATCH(302, "비밀번호 불일치"),


    //Board

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
