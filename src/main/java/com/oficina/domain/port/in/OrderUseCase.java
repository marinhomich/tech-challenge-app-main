package com.oficina.domain.port.in;

import com.oficina.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de entrada — define os casos de uso disponíveis para Ordens de Serviço.
 */
public interface OrderUseCase {

    Order createOrder(Order order);

    Order approveQuote(UUID orderId, boolean approved, String reason);

    Order startDiagnostic(UUID orderId);

    Order sendToApproval(UUID orderId);

    Order startExecution(UUID orderId);

    Order finishOrder(UUID orderId);

    Order deliverOrder(UUID orderId);

    Optional<Order> findById(UUID id);

    /** Lista ordens ativas (excluindo FINISHED e DELIVERED por padrão). */
    List<Order> findAllActive();

    /** Lista todas as ordens, incluindo finalizadas. */
    List<Order> findAll();

    Order updateOrder(Order order);

    void deleteById(UUID id);

    /** Retorna tempo médio de execução em segundos. */
    Double getAverageExecutionTimeSeconds();

    /** Envia notificação de status ao cliente (e-mail ou log). */
    void notifyStatus(UUID orderId);
}
