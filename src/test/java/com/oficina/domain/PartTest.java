package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class PartTest {

    @Test
    public void testPartCreation() {
        Part part = new Part("SKU123", "Oil Filter");
        
        assertNotNull(part.getId());
        assertEquals("SKU123", part.getSku());
        assertEquals("Oil Filter", part.getName());
        assertNotNull(part.getCreatedAt());
        assertEquals(0, part.getStockQty());
        assertEquals(0, part.getMinStock());
    }

    @Test
    public void testPartSettersAndGetters() {
        Part part = new Part();
        UUID id = UUID.randomUUID();
        
        part.setId(id);
        part.setSku("SKU456");
        part.setName("Air Filter");
        part.setStockQty(50);
        part.setCostPrice(new BigDecimal("10.00"));
        part.setSalePrice(new BigDecimal("15.00"));
        part.setMinStock(10);
        
        assertEquals(id, part.getId());
        assertEquals("SKU456", part.getSku());
        assertEquals("Air Filter", part.getName());
        assertEquals(50, part.getStockQty());
        assertEquals(new BigDecimal("10.00"), part.getCostPrice());
        assertEquals(new BigDecimal("15.00"), part.getSalePrice());
        assertEquals(10, part.getMinStock());
    }
}
