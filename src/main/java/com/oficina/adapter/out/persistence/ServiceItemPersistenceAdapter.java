package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.ServiceItemJpaEntity;
import com.oficina.adapter.out.persistence.jpa.ServiceItemJpaRepository;
import com.oficina.domain.model.ServiceItem;
import com.oficina.domain.port.out.ServiceItemRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ServiceItemPersistenceAdapter implements ServiceItemRepositoryPort {

    private final ServiceItemJpaRepository jpaRepository;

    public ServiceItemPersistenceAdapter(ServiceItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ServiceItem save(ServiceItem item) {
        return toDomain(jpaRepository.save(toEntity(item)));
    }

    @Override
    public Optional<ServiceItem> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ServiceItem> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private ServiceItem toDomain(ServiceItemJpaEntity e) {
        ServiceItem s = new ServiceItem();
        s.setId(e.getId());
        s.setCode(e.getCode());
        s.setDescription(e.getDescription());
        s.setHourlyRate(e.getHourlyRate());
        s.setEstimatedHours(e.getEstimatedHours());
        s.setActive(e.isActive());
        return s;
    }

    private ServiceItemJpaEntity toEntity(ServiceItem s) {
        ServiceItemJpaEntity e = new ServiceItemJpaEntity();
        e.setId(s.getId());
        e.setCode(s.getCode());
        e.setDescription(s.getDescription());
        e.setHourlyRate(s.getHourlyRate());
        e.setEstimatedHours(s.getEstimatedHours());
        e.setActive(s.isActive());
        return e;
    }
}
