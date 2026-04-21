package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class OrderPartTest {

    @Test
    public void testOrderPartCreation() {
        UUID partId = UUID.randomUUID();
        OrderPart orderPart = new OrderPart(partId, 5, new BigDecimal("20.00"));
        
        assertNotNull(orderPart.getId());
        assertEquals(partId, orderPart.getPartId());
        assertEquals(5, orderPart.getQuantity());
        assertEquals(new BigDecimal("20.00"), orderPart.getUnitCost());
        assertEquals(new BigDecimal("100.00"), orderPart.getTotalCost());
        assertFalse(orderPart.isReserved());
    }

    @Test
    public void testOrderPartSettersAndGetters() {
        OrderPart orderPart = new OrderPart();
        UUID id = UUID.randomUUID();
        UUID partId = UUID.randomUUID();
        Order order = new Order();
        
        orderPart.setId(id);
        orderPart.setPartId(partId);
        orderPart.setQuantity(10);
        orderPart.setUnitCost(new BigDecimal("15.00"));
        orderPart.setTotalCost(new BigDecimal("150.00"));
        orderPart.setReserved(true);
        orderPart.setOrder(order);
        
        assertEquals(id, orderPart.getId());
        assertEquals(partId, orderPart.getPartId());
        assertEquals(10, orderPart.getQuantity());
        assertEquals(new BigDecimal("15.00"), orderPart.getUnitCost());
        assertEquals(new BigDecimal("150.00"), orderPart.getTotalCost());
        assertTrue(orderPart.isReserved());
        assertEquals(order, orderPart.getOrder());
    }
}
