package com.oficina.domain.service;

import com.oficina.domain.model.Part;
import com.oficina.domain.port.out.PartRepositoryPort;
import com.oficina.domain.port.out.StockTransactionPort;
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

public class PartDomainServiceTest {

    @Mock
    private PartRepositoryPort repository;

    @Mock
    private StockTransactionPort stockTransactionPort;

    @InjectMocks
    private PartDomainService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSavePart() {
        Part part = new Part();
        when(repository.save(part)).thenReturn(part);

        Part result = service.save(part);

        assertNotNull(result);
        verify(repository, times(1)).save(part);
    }

    @Test
    void findById_shouldReturnPart() {
        UUID id = UUID.randomUUID();
        Part part = new Part();
        when(repository.findById(id)).thenReturn(Optional.of(part));

        assertTrue(service.findById(id).isPresent());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(Collections.singletonList(new Part()));

        List<Part> list = service.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deleteById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void adjustStock_existingPart_shouldUpdateStockAndRecordTransaction() {
        UUID partId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Part part = new Part();
        part.setStockQty(10);

        when(repository.findById(partId)).thenReturn(Optional.of(part));
        when(repository.save(any(Part.class))).thenReturn(part);

        Part result = service.adjustStock(partId, 5, userId);

        assertEquals(15, result.getStockQty());
        verify(repository, times(1)).save(part);
        verify(stockTransactionPort, times(1)).recordAdjustment(partId, 5, userId);
    }

    @Test
    void adjustStock_whenStockIsNull_shouldTreatAsZero() {
        UUID partId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Part part = new Part(); // stockQty is null

        when(repository.findById(partId)).thenReturn(Optional.of(part));
        when(repository.save(any(Part.class))).thenReturn(part);

        Part result = service.adjustStock(partId, 5, userId);

        assertEquals(5, result.getStockQty());
        verify(repository, times(1)).save(part);
        verify(stockTransactionPort, times(1)).recordAdjustment(partId, 5, userId);
    }

    @Test
    void adjustStock_nonExistingPart_shouldThrowException() {
        UUID partId = UUID.randomUUID();
        when(repository.findById(partId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.adjustStock(partId, 1, UUID.randomUUID()));
        verify(repository, never()).save(any());
        verify(stockTransactionPort, never()).recordAdjustment(any(), anyInt(), any());
    }
}
