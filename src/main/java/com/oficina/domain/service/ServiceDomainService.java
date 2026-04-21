package com.oficina.domain.service;

import com.oficina.domain.model.ServiceItem;
import com.oficina.domain.port.in.ServiceUseCase;
import com.oficina.domain.port.out.ServiceItemRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de domínio para Serviços da oficina.
 */
@Service
public class ServiceDomainService implements ServiceUseCase {

    private final ServiceItemRepositoryPort repository;

    public ServiceDomainService(ServiceItemRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public ServiceItem create(ServiceItem item) {
        return repository.save(item);
    }

    @Override
    public Optional<ServiceItem> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<ServiceItem> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public ServiceItem update(UUID id, ServiceItem updated) {
        ServiceItem existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ServiceItem not found: " + id));
        if (updated.getCode() != null) existing.setCode(updated.getCode());
        if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
        if (updated.getHourlyRate() != null) existing.setHourlyRate(updated.getHourlyRate());
        if (updated.getEstimatedHours() != null) existing.setEstimatedHours(updated.getEstimatedHours());
        return repository.save(existing);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
