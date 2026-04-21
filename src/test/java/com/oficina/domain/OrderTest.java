package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testOrderCreation() {
        UUID customerId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        Order order = new Order("OS-123", customerId, vehicleId);
        
        assertNotNull(order.getId());
        assertEquals("OS-123", order.getNumber());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(vehicleId, order.getVehicleId());
        assertEquals("RECEIVED", order.getStatus());
        assertNotNull(order.getCreatedAt());
        assertFalse(order.isApproved());
    }

    @Test
    public void testOrderSettersAndGetters() {
        Order order = new Order();
        UUID id = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID vehicleId = UUID.randomUUID();
        
        order.setId(id);
        order.setNumber("OS-456");
        order.setCustomerId(customerId);
        order.setVehicleId(vehicleId);
        order.setStatus("IN_EXECUTION");
        order.setTotalEstimated(new BigDecimal("100.00"));
        order.setTotalFinal(new BigDecimal("110.00"));
        order.setApproved(true);
        
        assertEquals(id, order.getId());
        assertEquals("OS-456", order.getNumber());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(vehicleId, order.getVehicleId());
        assertEquals("IN_EXECUTION", order.getStatus());
        assertEquals(new BigDecimal("100.00"), order.getTotalEstimated());
        assertEquals(new BigDecimal("110.00"), order.getTotalFinal());
        assertTrue(order.isApproved());
    }

    @Test
    public void testOrderItemsManagement() {
        Order order = new Order();
        OrderItem item = new OrderItem();
        
        order.addItem(item);
        assertEquals(1, order.getItems().size());
        assertEquals(order, item.getOrder());
    }

    @Test
    public void testOrderPartsManagement() {
        Order order = new Order();
        OrderPart part = new OrderPart();
        
        order.addPart(part);
        assertEquals(1, order.getParts().size());
        assertEquals(order, part.getOrder());
    }
}
