package com.oficina.domain;

import com.oficina.adapter.out.persistence.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        UserJpaEntity user = new UserJpaEntity("testuser", "encoded_password");
        
        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("encoded_password", user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testUserSettersAndGetters() {
        UserJpaEntity user = new UserJpaEntity();
        UUID id = UUID.randomUUID();
        
        user.setId(id);
        user.setUsername("admin");
        user.setPassword("hashed");
        user.setEnabled(false);
        
        assertEquals(id, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("hashed", user.getPassword());
        assertFalse(user.isEnabled());
    }
}
