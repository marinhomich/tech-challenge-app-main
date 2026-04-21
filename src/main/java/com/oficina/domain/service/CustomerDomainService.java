package com.oficina.domain.service;

import com.oficina.domain.model.Customer;
import com.oficina.domain.port.in.CustomerUseCase;
import com.oficina.domain.port.out.CustomerRepositoryPort;
import com.oficina.domain.validation.DocumentValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de domínio para Clientes.
 */
@Service
public class CustomerDomainService implements CustomerUseCase {

    private final CustomerRepositoryPort customerRepository;

    public CustomerDomainService(CustomerRepositoryPort customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Customer create(Customer customer) {
        if (!DocumentValidator.isValidDocument(customer.getDocument())) {
            throw new IllegalArgumentException("Invalid CPF/CNPJ: " + customer.getDocument());
        }
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByDocument(String document) {
        if (!DocumentValidator.isValidDocument(document)) {
            throw new IllegalArgumentException("Invalid CPF/CNPJ");
        }
        return customerRepository.findByDocument(document);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    @Override
    public Customer update(UUID id, Customer updated) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        if (updated.getDocument() != null && !DocumentValidator.isValidDocument(updated.getDocument())) {
            throw new IllegalArgumentException("Invalid CPF/CNPJ");
        }
        if (updated.getName() != null) existing.setName(updated.getName());
        if (updated.getDocument() != null) existing.setDocument(updated.getDocument());
        if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
        if (updated.getPhone() != null) existing.setPhone(updated.getPhone());
        return customerRepository.save(existing);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }
}
