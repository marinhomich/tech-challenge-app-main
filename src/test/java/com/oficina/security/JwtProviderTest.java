package com.oficina.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtProviderTest {

    @Test
    public void generate_and_validate_token() {
        JwtProvider provider = new JwtProvider("my-very-secret-key-that-is-long-enough-for-hs256");
        String token = provider.generateToken("user1");
        assertNotNull(token);
        assertTrue(provider.validateToken(token));
        String username = provider.getUsernameFromToken(token);
        assertEquals("user1", username);
    }
}
