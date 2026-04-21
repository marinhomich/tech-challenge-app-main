package com.oficina.domain.model;

/**
 * Estados possíveis de uma Ordem de Serviço, em ordem de prioridade de exibição (maior = mais urgente).
 */
public enum OrderStatus {
    DELIVERED(0),
    FINISHED(1),
    RECEIVED(2),
    IN_DIAGNOSTIC(3),
    AWAITING_APPROVAL(4),
    IN_EXECUTION(5);

    private final int priority;

    OrderStatus(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
