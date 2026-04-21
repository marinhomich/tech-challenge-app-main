package com.oficina.domain;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testVehicleCreation() {
        UUID customerId = UUID.randomUUID();
        Vehicle vehicle = new Vehicle(customerId, "ABC-1234");
        
        assertNotNull(vehicle.getId());
        assertEquals(customerId, vehicle.getCustomerId());
        assertEquals("ABC-1234", vehicle.getPlate());
        assertNotNull(vehicle.getCreatedAt());
    }

    @Test
    public void testVehicleSettersAndGetters() {
        Vehicle vehicle = new Vehicle();
        UUID id = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        
        vehicle.setId(id);
        vehicle.setCustomerId(customerId);
        vehicle.setPlate("XYZ-5678");
        vehicle.setBrand("Honda");
        vehicle.setModel("Civic");
        vehicle.setYear(2020);
        
        assertEquals(id, vehicle.getId());
        assertEquals(customerId, vehicle.getCustomerId());
        assertEquals("XYZ-5678", vehicle.getPlate());
        assertEquals("Honda", vehicle.getBrand());
        assertEquals("Civic", vehicle.getModel());
        assertEquals(2020, vehicle.getYear());
    }
}
