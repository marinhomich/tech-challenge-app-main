package com.oficina.domain.port.out;

import com.oficina.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída — abstrai a persistência de Ordens de Serviço.
 * O domínio depende desta interface; a implementação fica no adapter de persistência.
 */
public interface OrderRepositoryPort {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    /** Busca ordens excluindo FINISHED e DELIVERED (listagem ativa). */
    List<Order> findAllActive();

    void deleteById(UUID id);
}
