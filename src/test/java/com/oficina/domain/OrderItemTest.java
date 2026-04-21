package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    public void testOrderItemCreation() {
        UUID serviceId = UUID.randomUUID();
        OrderItem item = new OrderItem(serviceId, new BigDecimal("2"), new BigDecimal("50.00"));
        
        assertNotNull(item.getId());
        assertEquals(serviceId, item.getServiceId());
        assertEquals(new BigDecimal("2"), item.getQuantity());
        assertEquals(new BigDecimal("50.00"), item.getUnitPrice());
        assertEquals(new BigDecimal("100.00"), item.getTotalPrice());
    }

    @Test
    public void testOrderItemSettersAndGetters() {
        OrderItem item = new OrderItem();
        UUID id = UUID.randomUUID();
        UUID serviceId = UUID.randomUUID();
        Order order = new Order();
        
        item.setId(id);
        item.setServiceId(serviceId);
        item.setQuantity(new BigDecimal("3"));
        item.setUnitPrice(new BigDecimal("25.00"));
        item.setTotalPrice(new BigDecimal("75.00"));
        item.setOrder(order);
        
        assertEquals(id, item.getId());
        assertEquals(serviceId, item.getServiceId());
        assertEquals(new BigDecimal("3"), item.getQuantity());
        assertEquals(new BigDecimal("25.00"), item.getUnitPrice());
        assertEquals(new BigDecimal("75.00"), item.getTotalPrice());
        assertEquals(order, item.getOrder());
    }
}
