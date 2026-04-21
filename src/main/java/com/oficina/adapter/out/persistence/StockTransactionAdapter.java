package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.StockTransactionJpaEntity;
import com.oficina.adapter.out.persistence.jpa.StockTransactionJpaRepository;
import com.oficina.domain.port.out.StockTransactionPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adapter de persistência para transações de estoque.
 * Implementa StockTransactionPort usando o repositório JPA da camada de adapter.
 */
@Component
public class StockTransactionAdapter implements StockTransactionPort {

    private final StockTransactionJpaRepository jpaRepository;

    public StockTransactionAdapter(StockTransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void recordConsumption(UUID partId, UUID orderId, int quantity) {
        StockTransactionJpaEntity tx = new StockTransactionJpaEntity(partId, orderId, "CONSUMPTION", quantity, null);
        jpaRepository.save(tx);
    }

    @Override
    public void recordAdjustment(UUID partId, int delta, UUID userId) {
        StockTransactionJpaEntity tx = new StockTransactionJpaEntity(partId, null,
                delta >= 0 ? "ADJUSTMENT_IN" : "ADJUSTMENT_OUT", Math.abs(delta), userId);
        jpaRepository.save(tx);
    }
}
