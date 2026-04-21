package com.oficina.domain.port.in;

import com.oficina.domain.model.Part;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de entrada — casos de uso de Peças/Insumos.
 */
public interface PartUseCase {

    Part save(Part part);

    Optional<Part> findById(UUID id);

    List<Part> findAll();

    void deleteById(UUID id);

    Part adjustStock(UUID partId, int delta, UUID userId);
}
