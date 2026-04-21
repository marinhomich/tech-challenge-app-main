package com.oficina.domain.port.out;

import com.oficina.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída — abstrai a persistência de Clientes.
 */
public interface CustomerRepositoryPort {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByDocument(String document);

    List<Customer> findAll();

    void deleteById(UUID id);
}
