package com.oficina.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
// ...existing code...
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {
    @Test
    void testBeansCreation() throws Exception {
        JwtProvider jwtProvider = mock(JwtProvider.class);
        UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);
        SecurityConfig config = new SecurityConfig(jwtProvider, userDetailsService);

        // Test passwordEncoder bean
        PasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("123", encoder.encode("123")));

        // Test authenticationManager bean
        AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
        AuthenticationManager authManager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(authManager);
        assertEquals(authManager, config.authenticationManager(authConfig));
    }
}
