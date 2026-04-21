package com.oficina.domain.service;

import com.oficina.domain.model.Customer;
import com.oficina.domain.port.out.CustomerRepositoryPort;
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

public class CustomerDomainServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @InjectMocks
    private CustomerDomainService customerDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_withValidDocument_shouldSaveCustomer() {
        Customer customer = new Customer();
        customer.setDocument("11144477735"); // Valid default format
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer created = customerDomainService.create(customer);

        assertNotNull(created);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void create_withInvalidDocument_shouldThrowException() {
        Customer customer = new Customer();
        customer.setDocument("123"); // Invalid length

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerDomainService.create(customer));
        assertTrue(exception.getMessage().contains("Invalid CPF/CNPJ"));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void findById_shouldReturnCustomer() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerDomainService.findById(id);

        assertTrue(result.isPresent());
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void findByDocument_withValidDocument_shouldReturnCustomer() {
        String doc = "11144477735";
        Customer customer = new Customer();
        when(customerRepository.findByDocument(doc)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerDomainService.findByDocument(doc);

        assertTrue(result.isPresent());
        verify(customerRepository, times(1)).findByDocument(doc);
    }

    @Test
    void findByDocument_withInvalidDocument_shouldThrowException() {
        String doc = "abc";

        assertThrows(IllegalArgumentException.class, () -> customerDomainService.findByDocument(doc));
        verify(customerRepository, never()).findByDocument(any());
    }

    @Test
    void findAll_shouldReturnList() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(new Customer()));

        List<Customer> customers = customerDomainService.findAll();

        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
    }

    @Test
    void update_existingCustomer_withValidData_shouldSave() {
        UUID id = UUID.randomUUID();
        Customer existing = new Customer();
        existing.setDocument("11222333000181");

        Customer updated = new Customer();
        updated.setDocument("11144477735");
        updated.setName("New Name");
        updated.setEmail("email@test.com");
        updated.setPhone("1234");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);

        Customer result = customerDomainService.update(id, updated);

        assertNotNull(result);
        assertEquals("11144477735", existing.getDocument());
        assertEquals("New Name", existing.getName());
        verify(customerRepository, times(1)).save(existing);
    }

    @Test
    void update_nonExisting_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerDomainService.update(id, new Customer()));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void delete_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(customerRepository).deleteById(id);

        customerDomainService.delete(id);

        verify(customerRepository, times(1)).deleteById(id);
    }
}
