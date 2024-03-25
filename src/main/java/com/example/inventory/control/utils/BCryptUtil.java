package com.example.inventory.control.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class BCryptUtil {

    private static BCryptPasswordEncoder PASSWORD_ENCODER = null;

    private BCryptUtil() {
    }

    public static String encode(String value) {
        if (PASSWORD_ENCODER == null) {
            PASSWORD_ENCODER = new BCryptPasswordEncoder();
        }
        return PASSWORD_ENCODER.encode(value);
    }
}
