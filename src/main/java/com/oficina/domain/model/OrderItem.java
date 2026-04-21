package com.oficina.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidade de domínio pura — representa um serviço incluído em uma OS.
 */
public class OrderItem {

    private UUID id;
    private UUID serviceId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public OrderItem() {}

    public OrderItem(UUID serviceId, BigDecimal quantity, BigDecimal unitPrice) {
        this.id = UUID.randomUUID();
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        if (unitPrice != null && quantity != null) {
            this.totalPrice = unitPrice.multiply(quantity);
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
