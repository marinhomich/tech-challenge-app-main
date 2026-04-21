package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.VehicleJpaEntity;
import com.oficina.adapter.out.persistence.jpa.VehicleJpaRepository;
import com.oficina.domain.model.Vehicle;
import com.oficina.domain.port.out.VehicleRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {

    private final VehicleJpaRepository jpaRepository;

    public VehiclePersistenceAdapter(VehicleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return toDomain(jpaRepository.save(toEntity(vehicle)));
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Vehicle> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    private Vehicle toDomain(VehicleJpaEntity e) {
        Vehicle v = new Vehicle();
        v.setId(e.getId());
        v.setCustomerId(e.getCustomerId());
        v.setPlate(e.getPlate());
        v.setBrand(e.getBrand());
        v.setModel(e.getModel());
        v.setYear(e.getYear());
        v.setCreatedAt(e.getCreatedAt());
        return v;
    }

    private VehicleJpaEntity toEntity(Vehicle v) {
        VehicleJpaEntity e = new VehicleJpaEntity();
        e.setId(v.getId());
        e.setCustomerId(v.getCustomerId());
        e.setPlate(v.getPlate());
        e.setBrand(v.getBrand());
        e.setModel(v.getModel());
        e.setYear(v.getYear());
        e.setCreatedAt(v.getCreatedAt());
        return e;
    }
}
