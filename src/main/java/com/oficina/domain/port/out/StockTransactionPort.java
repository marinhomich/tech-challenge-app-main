package com.oficina.domain.port.out;

import java.util.UUID;

/**
 * Porta de saída — abstrai o registro de transações de estoque.
 */
public interface StockTransactionPort {

    void recordConsumption(UUID partId, UUID orderId, int quantity);

    void recordAdjustment(UUID partId, int delta, UUID userId);
}
