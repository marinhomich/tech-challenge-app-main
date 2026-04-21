package com.oficina.security;

import com.oficina.adapter.out.persistence.entity.UserJpaEntity;
import com.oficina.adapter.out.persistence.jpa.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {
    @Test
    void testLoadUserByUsernameFound() {
        UserJpaRepository repo = mock(UserJpaRepository.class);
        UserJpaEntity user = mock(UserJpaEntity.class);
        when(repo.findByUsername("user")).thenReturn(Optional.of(user));
        UserDetailsServiceImpl service = new UserDetailsServiceImpl(repo);
        assertEquals(user, service.loadUserByUsername("user"));
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        UserJpaRepository repo = mock(UserJpaRepository.class);
        when(repo.findByUsername("user")).thenReturn(Optional.empty());
        UserDetailsServiceImpl service = new UserDetailsServiceImpl(repo);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("user"));
    }
}
