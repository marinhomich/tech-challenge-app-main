package com.oficina.tools;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class BCryptGeneratorTest {
    @Test
    void testBCryptEncode() {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        String raw = "Challenge@2024";
        String hash = enc.encode(raw);
        assertTrue(enc.matches(raw, hash));
    }
}
