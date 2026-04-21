package com.oficina.domain.port.in;

import com.oficina.domain.model.ServiceItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de entrada — casos de uso de Serviços.
 */
public interface ServiceUseCase {

    ServiceItem create(ServiceItem item);

    Optional<ServiceItem> findById(UUID id);

    List<ServiceItem> findAll();

    ServiceItem update(UUID id, ServiceItem item);

    void delete(UUID id);
}
