package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.CustomerJpaEntity;
import com.oficina.adapter.out.persistence.jpa.CustomerJpaRepository;
import com.oficina.domain.model.Customer;
import com.oficina.domain.port.out.CustomerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final CustomerJpaRepository jpaRepository;

    public CustomerPersistenceAdapter(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return toDomain(jpaRepository.save(toEntity(customer)));
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByDocument(String document) {
        return jpaRepository.findByDocument(document).map(this::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private Customer toDomain(CustomerJpaEntity e) {
        Customer c = new Customer();
        c.setId(e.getId());
        c.setName(e.getName());
        c.setDocument(e.getDocument());
        c.setEmail(e.getEmail());
        c.setPhone(e.getPhone());
        c.setCreatedAt(e.getCreatedAt());
        return c;
    }

    private CustomerJpaEntity toEntity(Customer c) {
        CustomerJpaEntity e = new CustomerJpaEntity();
        e.setId(c.getId());
        e.setName(c.getName());
        e.setDocument(c.getDocument());
        e.setEmail(c.getEmail());
        e.setPhone(c.getPhone());
        e.setCreatedAt(c.getCreatedAt());
        return e;
    }
}
