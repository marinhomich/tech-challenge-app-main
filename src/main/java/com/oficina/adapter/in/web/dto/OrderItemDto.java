package com.oficina.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemDto {

    @NotNull(message = "serviceId é obrigatório")
    private UUID serviceId;

    @NotNull(message = "quantity é obrigatório")
    @Positive(message = "quantity deve ser positivo")
    private BigDecimal quantity;

    @NotNull(message = "unitPrice é obrigatório")
    @Positive(message = "unitPrice deve ser positivo")
    private BigDecimal unitPrice;

    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
