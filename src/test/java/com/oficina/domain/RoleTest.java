package com.oficina.domain;

import com.oficina.adapter.out.persistence.entity.RoleJpaEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void testRoleCreation() {
        RoleJpaEntity role = new RoleJpaEntity("ADMIN");
        
        assertNotNull(role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    public void testRoleSettersAndGetters() {
        RoleJpaEntity role = new RoleJpaEntity();
        
        role.setName("USER");
        
        assertEquals("USER", role.getName());
    }
}
