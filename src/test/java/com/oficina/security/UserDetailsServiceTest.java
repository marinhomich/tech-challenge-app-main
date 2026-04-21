package com.oficina.security;

import com.oficina.adapter.out.persistence.entity.RoleJpaEntity;
import com.oficina.adapter.out.persistence.entity.UserJpaEntity;
import com.oficina.adapter.out.persistence.jpa.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    private UserJpaRepository userRepository;
    private UserDetailsServiceImpl uds;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserJpaRepository.class);
        uds = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void loadUserByUsername_returns_user() {
        UserJpaEntity u = new UserJpaEntity("admin", "pass");
        u.setId(UUID.randomUUID());
        u.getRoles().add(new RoleJpaEntity("ROLE_ADMIN"));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(u));
        var loaded = uds.loadUserByUsername("admin");
        assertEquals("admin", loaded.getUsername());
    }
}
