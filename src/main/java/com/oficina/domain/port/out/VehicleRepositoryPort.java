package com.oficina.domain.port.out;

import com.oficina.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída — abstrai a persistência de Veículos.
 */
public interface VehicleRepositoryPort {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(UUID id);

    List<Vehicle> findAll();

    void deleteById(UUID id);
}
