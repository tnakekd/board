package com.kbhc.board.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

public class CommonUtil {

    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static String encodeLoginInfo(String email, String password) {
        String loginInfo = String.join(":", email, password);
        return String.format("Basic %s", Base64.getEncoder().encodeToString(loginInfo.getBytes()));
    }

}
