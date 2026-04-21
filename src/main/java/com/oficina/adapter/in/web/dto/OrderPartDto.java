package com.oficina.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderPartDto {

    @NotNull(message = "partId é obrigatório")
    private UUID partId;

    @NotNull(message = "quantity é obrigatório")
    @Positive(message = "quantity deve ser positivo")
    private Integer quantity;

    @NotNull(message = "unitCost é obrigatório")
    @Positive(message = "unitCost deve ser positivo")
    private BigDecimal unitCost;

    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
}
