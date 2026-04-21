package com.oficina.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.util.ReflectionTestUtils;

class JwtProviderTest {

    @Test
    void testValidateToken_Invalid() {
        JwtProvider provider = new JwtProvider("minha_chave_super_secreta_padrao_para_desenvolvimento_local_de_mais_de_256_bits_123");
        assertFalse(provider.validateToken("invalidToken"));
    }
}
