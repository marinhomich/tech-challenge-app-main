package com.oficina.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Test
    void testPasswordEncoder() {
        JwtProvider jwtProvider = mock(JwtProvider.class);
        SecurityConfig config = new SecurityConfig(jwtProvider);
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
    }
}
