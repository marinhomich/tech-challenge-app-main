package com.oficina.domain.service;

import com.oficina.domain.model.Vehicle;
import com.oficina.domain.port.in.VehicleUseCase;
import com.oficina.domain.port.out.VehicleRepositoryPort;
import com.oficina.domain.validation.PlateValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de domínio para Veículos.
 */
@Service
public class VehicleDomainService implements VehicleUseCase {

    private final VehicleRepositoryPort vehicleRepository;

    public VehicleDomainService(VehicleRepositoryPort vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    @Override
    public Vehicle create(Vehicle vehicle) {
        if (!PlateValidator.isValidPlate(vehicle.getPlate())) {
            throw new IllegalArgumentException("Invalid vehicle plate format: " + vehicle.getPlate());
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional
    @Override
    public Vehicle update(UUID id, Vehicle updated) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id));
        if (updated.getPlate() != null && !PlateValidator.isValidPlate(updated.getPlate())) {
            throw new IllegalArgumentException("Invalid vehicle plate format");
        }
        if (updated.getPlate() != null) existing.setPlate(updated.getPlate());
        if (updated.getBrand() != null) existing.setBrand(updated.getBrand());
        if (updated.getModel() != null) existing.setModel(updated.getModel());
        if (updated.getYear() != null) existing.setYear(updated.getYear());
        return vehicleRepository.save(existing);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        vehicleRepository.deleteById(id);
    }
}
