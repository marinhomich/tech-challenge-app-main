package com.oficina.domain.port.out;

import com.oficina.domain.model.Customer;
import com.oficina.domain.model.Order;

/**
 * Porta de saída — abstrai o envio de notificações aos clientes.
 */
public interface NotificationPort {

    /**
     * Notifica o cliente sobre o status atual da OS.
     *
     * @param order    a ordem de serviço
     * @param customer o cliente proprietário da OS
     */
    void notifyOrderStatus(Order order, Customer customer);
}
