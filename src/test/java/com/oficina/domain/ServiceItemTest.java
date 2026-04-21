package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceItemTest {

    @Test
    public void testServiceItemCreation() {
        ServiceItem service = new ServiceItem("SRV001", "Oil Change");
        
        assertNotNull(service.getId());
        assertEquals("SRV001", service.getCode());
        assertEquals("Oil Change", service.getDescription());
        assertTrue(service.isActive());
    }

    @Test
    public void testServiceItemSettersAndGetters() {
        ServiceItem service = new ServiceItem();
        UUID id = UUID.randomUUID();
        
        service.setId(id);
        service.setCode("SRV002");
        service.setDescription("Brake Service");
        service.setHourlyRate(new BigDecimal("50.00"));
        service.setEstimatedHours(new BigDecimal("2.5"));
        service.setActive(false);
        
        assertEquals(id, service.getId());
        assertEquals("SRV002", service.getCode());
        assertEquals("Brake Service", service.getDescription());
        assertEquals(new BigDecimal("50.00"), service.getHourlyRate());
        assertEquals(new BigDecimal("2.5"), service.getEstimatedHours());
        assertFalse(service.isActive());
    }
}
