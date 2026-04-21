package com.oficina.domain.service;

import com.oficina.domain.model.Vehicle;
import com.oficina.domain.port.out.VehicleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleDomainServiceTest {

    @Mock
    private VehicleRepositoryPort repository;

    @InjectMocks
    private VehicleDomainService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_withValidPlate_shouldSave() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC1234");
        when(repository.save(vehicle)).thenReturn(vehicle);

        Vehicle result = service.create(vehicle);

        assertNotNull(result);
        verify(repository, times(1)).save(vehicle);
    }

    @Test
    void create_withInvalidPlate_shouldThrowException() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.create(vehicle));
        assertTrue(exception.getMessage().contains("Invalid vehicle plate"));
        verify(repository, never()).save(any());
    }

    @Test
    void findById_shouldReturnVehicle() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(new Vehicle()));

        assertTrue(service.findById(id).isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(Collections.singletonList(new Vehicle()));

        List<Vehicle> list = service.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update_withValidPlate_shouldSave() {
        UUID id = UUID.randomUUID();
        Vehicle existing = new Vehicle();
        existing.setPlate("OLD1234");
        Vehicle updated = new Vehicle();
        updated.setPlate("NEW1234");
        updated.setBrand("Brand");
        updated.setModel("Model");
        updated.setYear(2020);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Vehicle.class))).thenReturn(existing);

        Vehicle result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("NEW1234", existing.getPlate());
        assertEquals("Brand", existing.getBrand());
        assertEquals("Model", existing.getModel());
        assertEquals(2020, existing.getYear());
        verify(repository, times(1)).save(existing);
    }

    @Test
    void update_withInvalidPlate_shouldThrowException() {
        UUID id = UUID.randomUUID();
        Vehicle existing = new Vehicle();
        Vehicle updated = new Vehicle();
        updated.setPlate("INVALID");

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class, () -> service.update(id, updated));
        verify(repository, never()).save(any());
    }

    @Test
    void update_nonExisting_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.update(id, new Vehicle()));
    }

    @Test
    void delete_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
