package com.oficina.adapter.out.persistence;

import com.oficina.adapter.out.persistence.entity.OrderItemJpaEntity;
import com.oficina.adapter.out.persistence.entity.OrderJpaEntity;
import com.oficina.adapter.out.persistence.entity.OrderPartJpaEntity;
import com.oficina.domain.model.Order;
import com.oficina.domain.model.OrderItem;
import com.oficina.domain.model.OrderPart;
import com.oficina.adapter.out.persistence.entity.*;
import com.oficina.adapter.out.persistence.jpa.OrderJpaRepository;
import com.oficina.domain.model.*;
import com.oficina.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter de persistência para Ordens de Serviço.
 * Implementa OrderRepositoryPort usando Spring Data JPA.
 */
@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;

    public OrderPersistenceAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Order> findAllActive() {
        return jpaRepository.findAllActive().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    // ─── Mapping ────────────────────────────────────────────────────────────────

    private Order toDomain(OrderJpaEntity e) {
        Order o = new Order();
        o.setId(e.getId());
        o.setNumber(e.getNumber());
        o.setCustomerId(e.getCustomerId());
        o.setVehicleId(e.getVehicleId());
        o.setStatus(e.getStatus());
        o.setTotalEstimated(e.getTotalEstimated());
        o.setTotalFinal(e.getTotalFinal());
        o.setApproved(e.isApproved());
        o.setApprovalRejectedReason(e.getApprovalRejectedReason());
        o.setReceivedAt(e.getReceivedAt());
        o.setDiagnosticAt(e.getDiagnosticAt());
        o.setAwaitingApprovalAt(e.getAwaitingApprovalAt());
        o.setStartedAt(e.getStartedAt());
        o.setFinishedAt(e.getFinishedAt());
        o.setDeliveredAt(e.getDeliveredAt());
        o.setCreatedAt(e.getCreatedAt());
        o.setDeletedAt(e.getDeletedAt());
        if (e.getItems() != null) {
            o.setItems(e.getItems().stream().map(this::toDomainItem).collect(Collectors.toList()));
        }
        if (e.getParts() != null) {
            o.setParts(e.getParts().stream().map(this::toDomainPart).collect(Collectors.toList()));
        }
        return o;
    }

    private OrderJpaEntity toEntity(Order o) {
        OrderJpaEntity e = new OrderJpaEntity();
        e.setId(o.getId());
        e.setNumber(o.getNumber());
        e.setCustomerId(o.getCustomerId());
        e.setVehicleId(o.getVehicleId());
        e.setStatus(o.getStatus());
        e.setTotalEstimated(o.getTotalEstimated());
        e.setTotalFinal(o.getTotalFinal());
        e.setApproved(o.isApproved());
        e.setApprovalRejectedReason(o.getApprovalRejectedReason());
        e.setReceivedAt(o.getReceivedAt());
        e.setDiagnosticAt(o.getDiagnosticAt());
        e.setAwaitingApprovalAt(o.getAwaitingApprovalAt());
        e.setStartedAt(o.getStartedAt());
        e.setFinishedAt(o.getFinishedAt());
        e.setDeliveredAt(o.getDeliveredAt());
        e.setCreatedAt(o.getCreatedAt());
        e.setDeletedAt(o.getDeletedAt());
        if (o.getItems() != null) {
            List<OrderItemJpaEntity> items = o.getItems().stream().map(i -> {
                OrderItemJpaEntity ie = toEntityItem(i);
                ie.setOrder(e);
                return ie;
            }).collect(Collectors.toList());
            e.setItems(items);
        }
        if (o.getParts() != null) {
            List<OrderPartJpaEntity> parts = o.getParts().stream().map(p -> {
                OrderPartJpaEntity pe = toEntityPart(p);
                pe.setOrder(e);
                return pe;
            }).collect(Collectors.toList());
            e.setParts(parts);
        }
        return e;
    }

    private OrderItem toDomainItem(OrderItemJpaEntity ie) {
        OrderItem i = new OrderItem();
        i.setId(ie.getId());
        i.setServiceId(ie.getServiceId());
        i.setQuantity(ie.getQuantity());
        i.setUnitPrice(ie.getUnitPrice());
        i.setTotalPrice(ie.getTotalPrice());
        return i;
    }

    private OrderItemJpaEntity toEntityItem(OrderItem i) {
        OrderItemJpaEntity ie = new OrderItemJpaEntity();
        ie.setId(i.getId());
        ie.setServiceId(i.getServiceId());
        ie.setQuantity(i.getQuantity());
        ie.setUnitPrice(i.getUnitPrice());
        ie.setTotalPrice(i.getTotalPrice());
        return ie;
    }

    private OrderPart toDomainPart(OrderPartJpaEntity pe) {
        OrderPart p = new OrderPart();
        p.setId(pe.getId());
        p.setPartId(pe.getPartId());
        p.setQuantity(pe.getQuantity());
        p.setUnitCost(pe.getUnitCost());
        p.setTotalCost(pe.getTotalCost());
        p.setReserved(pe.isReserved());
        return p;
    }

    private OrderPartJpaEntity toEntityPart(OrderPart p) {
        OrderPartJpaEntity pe = new OrderPartJpaEntity();
        pe.setId(p.getId());
        pe.setPartId(p.getPartId());
        pe.setQuantity(p.getQuantity());
        pe.setUnitCost(p.getUnitCost());
        pe.setTotalCost(p.getTotalCost());
        pe.setReserved(p.isReserved());
        return pe;
    }
}
