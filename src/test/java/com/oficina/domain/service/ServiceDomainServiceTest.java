package com.oficina.domain.service;

import com.oficina.domain.model.ServiceItem;
import com.oficina.domain.port.out.ServiceItemRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceDomainServiceTest {

    @Mock
    private ServiceItemRepositoryPort repository;

    @InjectMocks
    private ServiceDomainService serviceDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveServiceItem() {
        ServiceItem item = new ServiceItem();
        when(repository.save(item)).thenReturn(item);

        ServiceItem result = serviceDomainService.create(item);

        assertNotNull(result);
        verify(repository, times(1)).save(item);
    }

    @Test
    void findById_shouldReturnItem() {
        UUID id = UUID.randomUUID();
        ServiceItem item = new ServiceItem();
        when(repository.findById(id)).thenReturn(Optional.of(item));

        Optional<ServiceItem> result = serviceDomainService.findById(id);

        assertTrue(result.isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(Collections.singletonList(new ServiceItem()));

        List<ServiceItem> list = serviceDomainService.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void update_existing_shouldSave() {
        UUID id = UUID.randomUUID();
        ServiceItem existing = new ServiceItem();
        ServiceItem updated = new ServiceItem();
        updated.setCode("NEW-C");
        updated.setDescription("Desc");
        updated.setHourlyRate(new BigDecimal("100"));
        updated.setEstimatedHours(new BigDecimal("2.0"));

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(ServiceItem.class))).thenReturn(existing);

        ServiceItem result = serviceDomainService.update(id, updated);

        assertNotNull(result);
        assertEquals("NEW-C", existing.getCode());
        assertEquals("Desc", existing.getDescription());
        assertEquals(new BigDecimal("100"), existing.getHourlyRate());
        assertEquals(new BigDecimal("2.0"), existing.getEstimatedHours());
        verify(repository, times(1)).save(existing);
    }

    @Test
    void update_nonExisting_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> serviceDomainService.update(id, new ServiceItem()));
    }

    @Test
    void delete_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        serviceDomainService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }
}
