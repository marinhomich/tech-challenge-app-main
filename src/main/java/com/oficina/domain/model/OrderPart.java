package com.oficina.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidade de domínio pura — representa uma peça/insumo incluído em uma OS.
 */
public class OrderPart {

    private UUID id;
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private boolean reserved;

    public OrderPart() {}

    public OrderPart(UUID partId, Integer quantity, BigDecimal unitCost) {
        this.id = UUID.randomUUID();
        this.partId = partId;
        this.quantity = quantity;
        this.unitCost = unitCost;
        if (unitCost != null && quantity != null) {
            this.totalCost = unitCost.multiply(new BigDecimal(quantity));
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public boolean isReserved() { return reserved; }
    public void setReserved(boolean reserved) { this.reserved = reserved; }
}
