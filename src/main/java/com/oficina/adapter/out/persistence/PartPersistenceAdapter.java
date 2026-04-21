package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.PartJpaEntity;
import com.oficina.adapter.out.persistence.jpa.PartJpaRepository;
import com.oficina.domain.model.Part;
import com.oficina.domain.port.out.PartRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PartPersistenceAdapter implements PartRepositoryPort {

    private final PartJpaRepository jpaRepository;

    public PartPersistenceAdapter(PartJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Part save(Part part) {
        return toDomain(jpaRepository.save(toEntity(part)));
    }

    @Override
    public Optional<Part> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Part> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private Part toDomain(PartJpaEntity e) {
        Part p = new Part();
        p.setId(e.getId());
        p.setSku(e.getSku());
        p.setName(e.getName());
        p.setStockQty(e.getStockQty());
        p.setCostPrice(e.getCostPrice());
        p.setSalePrice(e.getSalePrice());
        p.setMinStock(e.getMinStock());
        p.setCreatedAt(e.getCreatedAt());
        return p;
    }

    private PartJpaEntity toEntity(Part p) {
        PartJpaEntity e = new PartJpaEntity();
        e.setId(p.getId());
        e.setSku(p.getSku());
        e.setName(p.getName());
        e.setStockQty(p.getStockQty());
        e.setCostPrice(p.getCostPrice());
        e.setSalePrice(p.getSalePrice());
        e.setMinStock(p.getMinStock());
        e.setCreatedAt(p.getCreatedAt());
        return e;
    }
}
