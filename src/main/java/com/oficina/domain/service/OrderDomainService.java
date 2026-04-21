package com.oficina.domain.service;

import com.oficina.domain.model.Customer;
import com.oficina.domain.model.Order;
import com.oficina.domain.model.OrderItem;
import com.oficina.domain.model.OrderPart;
import com.oficina.domain.model.OrderStatus;
import com.oficina.domain.port.in.OrderUseCase;
import com.oficina.domain.port.out.CustomerRepositoryPort;
import com.oficina.domain.port.out.NotificationPort;
import com.oficina.domain.port.out.OrderRepositoryPort;
import com.oficina.domain.port.out.PartRepositoryPort;
import com.oficina.domain.port.out.StockTransactionPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de domínio para Ordens de Serviço.
 * Implementa OrderUseCase (port/in) e depende apenas de ports (port/out).
 */
@Service
public class OrderDomainService implements OrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final PartRepositoryPort partRepository;
    private final StockTransactionPort stockTransactionPort;
    private final CustomerRepositoryPort customerRepository;
    private final NotificationPort notificationPort;

    public OrderDomainService(
            OrderRepositoryPort orderRepository,
            PartRepositoryPort partRepository,
            StockTransactionPort stockTransactionPort,
            CustomerRepositoryPort customerRepository,
            NotificationPort notificationPort) {
        this.orderRepository = orderRepository;
        this.partRepository = partRepository;
        this.stockTransactionPort = stockTransactionPort;
        this.customerRepository = customerRepository;
        this.notificationPort = notificationPort;
    }

    @Transactional
    @Override
    public Order createOrder(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        if (order.getItems() != null) {
            for (OrderItem it : order.getItems()) {
                if (it.getTotalPrice() == null && it.getUnitPrice() != null && it.getQuantity() != null) {
                    it.setTotalPrice(it.getUnitPrice().multiply(it.getQuantity()));
                }
                total = total.add(it.getTotalPrice() == null ? BigDecimal.ZERO : it.getTotalPrice());
            }
        }
        if (order.getParts() != null) {
            for (OrderPart op : order.getParts()) {
                total = total.add(op.getTotalCost() == null ? BigDecimal.ZERO : op.getTotalCost());
            }
        }
        order.setTotalEstimated(total);
        order.setStatus(OrderStatus.RECEIVED.name());
        order.setReceivedAt(Instant.now());
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order approveQuote(UUID orderId, boolean approved, String reason) {
        Order order = findByIdOrThrow(orderId);
        if (approved) {
            if (order.getParts() != null) {
                for (OrderPart op : order.getParts()) {
                    var part = partRepository.findById(op.getPartId())
                            .orElseThrow(() -> new IllegalArgumentException("Part not found: " + op.getPartId()));
                    int current = part.getStockQty() == null ? 0 : part.getStockQty();
                    if (current < op.getQuantity()) {
                        throw new IllegalStateException("Insufficient stock for part " + op.getPartId());
                    }
                    part.setStockQty(current - op.getQuantity());
                    partRepository.save(part);
                    stockTransactionPort.recordConsumption(op.getPartId(), order.getId(), op.getQuantity());
                    op.setReserved(false);
                }
            }
            order.setApproved(true);
        } else {
            order.setApproved(false);
            order.setApprovalRejectedReason(reason);
            order.setStatus(OrderStatus.RECEIVED.name());
        }
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order startDiagnostic(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        order.setStatus(OrderStatus.IN_DIAGNOSTIC.name());
        order.setDiagnosticAt(Instant.now());
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order sendToApproval(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        order.setStatus(OrderStatus.AWAITING_APPROVAL.name());
        order.setAwaitingApprovalAt(Instant.now());
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order startExecution(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        if (!order.isApproved()) {
            throw new IllegalStateException("Order must be approved before execution");
        }
        order.setStatus(OrderStatus.IN_EXECUTION.name());
        order.setStartedAt(Instant.now());
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order finishOrder(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        order.setStatus(OrderStatus.FINISHED.name());
        order.setFinishedAt(Instant.now());
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order deliverOrder(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        order.setStatus(OrderStatus.DELIVERED.name());
        order.setDeliveredAt(Instant.now());
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllActive() {
        return orderRepository.findAllActive().stream()
                .sorted(Comparator
                        .comparingInt((Order o) -> {
                            try {
                                return OrderStatus.valueOf(o.getStatus()).getPriority();
                            } catch (Exception e) {
                                return -1;
                            }
                        })
                        .reversed()
                        .thenComparing(o -> o.getCreatedAt() != null ? o.getCreatedAt() : Instant.MIN))
                .toList();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Double getAverageExecutionTimeSeconds() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getStartedAt() != null && o.getFinishedAt() != null)
                .mapToLong(o -> o.getFinishedAt().getEpochSecond() - o.getStartedAt().getEpochSecond())
                .average()
                .orElse(0.0);
    }

    @Override
    public void notifyStatus(UUID orderId) {
        Order order = findByIdOrThrow(orderId);
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        notificationPort.notifyOrderStatus(order, customer);
    }

    private Order findByIdOrThrow(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }
}
