package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testCustomerCreation() {
        Customer customer = new Customer("John Doe", "11144477735");
        
        assertNotNull(customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("11144477735", customer.getDocument());
        assertNotNull(customer.getCreatedAt());
    }

    @Test
    public void testCustomerSettersAndGetters() {
        Customer customer = new Customer();
        UUID id = UUID.randomUUID();
        
        customer.setId(id);
        customer.setName("Jane Doe");
        customer.setDocument("98765432100");
        customer.setEmail("jane@example.com");
        customer.setPhone("11999999999");
        
        assertEquals(id, customer.getId());
        assertEquals("Jane Doe", customer.getName());
        assertEquals("98765432100", customer.getDocument());
        assertEquals("jane@example.com", customer.getEmail());
        assertEquals("11999999999", customer.getPhone());
    }
}
