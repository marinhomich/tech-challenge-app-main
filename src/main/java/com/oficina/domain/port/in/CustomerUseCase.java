package com.oficina.domain.port.in;

import com.oficina.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de entrada — casos de uso de Clientes.
 */
public interface CustomerUseCase {

    Customer create(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByDocument(String document);

    List<Customer> findAll();

    Customer update(UUID id, Customer customer);

    void delete(UUID id);
}
