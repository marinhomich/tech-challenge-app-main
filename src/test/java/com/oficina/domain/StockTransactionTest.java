package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class StockTransactionTest {

    @Test
    public void testStockTransactionCreation() {
        UUID partId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        StockTransaction tx = new StockTransaction(partId, orderId, "ADJUSTMENT_IN", 10, userId);
        
        assertNotNull(tx.getId());
        assertEquals(partId, tx.getPartId());
        assertEquals(orderId, tx.getOrderId());
        assertEquals("ADJUSTMENT_IN", tx.getType());
        assertEquals(10, tx.getQty());
        assertEquals(userId, tx.getUserId());
        assertNotNull(tx.getCreatedAt());
    }

    @Test
    public void testStockTransactionSettersAndGetters() {
        StockTransaction tx = new StockTransaction();
        UUID id = UUID.randomUUID();
        UUID partId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        
        tx.setId(id);
        tx.setPartId(partId);
        tx.setOrderId(orderId);
        tx.setType("CONSUMPTION");
        tx.setQty(5);
        tx.setUserId(userId);
        
        assertEquals(id, tx.getId());
        assertEquals(partId, tx.getPartId());
        assertEquals(orderId, tx.getOrderId());
        assertEquals("CONSUMPTION", tx.getType());
        assertEquals(5, tx.getQty());
        assertEquals(userId, tx.getUserId());
    }
}
