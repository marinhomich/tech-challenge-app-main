package com.oficina.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

// ...existing code...
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
    @Test
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        JwtProvider jwtProvider = mock(JwtProvider.class);
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(jwtProvider.validateToken("validtoken")).thenReturn(true);
        when(jwtProvider.getUsernameFromToken("validtoken")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);

        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternalWithNoToken() throws ServletException, IOException {
        JwtProvider jwtProvider = mock(JwtProvider.class);
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userDetailsService);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);
        verify(chain).doFilter(request, response);
    }
}
