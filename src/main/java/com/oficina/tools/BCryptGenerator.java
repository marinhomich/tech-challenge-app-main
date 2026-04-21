package com.oficina.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        String raw = "Challenge@2024";
        System.out.println(enc.encode(raw));
    }
}
