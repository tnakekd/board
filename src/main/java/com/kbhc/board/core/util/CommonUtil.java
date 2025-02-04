package com.kbhc.board.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonUtil {

    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
