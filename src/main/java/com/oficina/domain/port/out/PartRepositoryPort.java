package com.oficina.domain.port.out;

import com.oficina.domain.model.Part;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída — abstrai a persistência de Peças/Insumos.
 */
public interface PartRepositoryPort {

    Part save(Part part);

    Optional<Part> findById(UUID id);

    List<Part> findAll();

    void deleteById(UUID id);
}
