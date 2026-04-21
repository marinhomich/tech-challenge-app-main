package com.oficina.domain.port.out;

import com.oficina.domain.model.ServiceItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída — abstrai a persistência de Serviços.
 */
public interface ServiceItemRepositoryPort {

    ServiceItem save(ServiceItem item);

    Optional<ServiceItem> findById(UUID id);

    List<ServiceItem> findAll();

    void deleteById(UUID id);
}
