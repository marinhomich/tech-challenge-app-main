package com.oficina.domain.port.in;

import com.oficina.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de entrada — casos de uso de Veículos.
 */
public interface VehicleUseCase {

    Vehicle create(Vehicle vehicle);

    Optional<Vehicle> findById(UUID id);

    List<Vehicle> findAll();

    Vehicle update(UUID id, Vehicle vehicle);

    void delete(UUID id);
}
