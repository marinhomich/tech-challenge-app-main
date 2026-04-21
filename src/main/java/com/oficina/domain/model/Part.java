package com.oficina.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem dependências de framework.
 */
public class Part {

    private UUID id;
    private String sku;
    private String name;
    private Integer stockQty;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer minStock;
    private Instant createdAt;

    public Part() {}

    public Part(String sku, String name) {
        this.id = UUID.randomUUID();
        this.sku = sku;
        this.name = name;
        this.stockQty = 0;
        this.minStock = 0;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getStockQty() { return stockQty; }
    public void setStockQty(Integer stockQty) { this.stockQty = stockQty; }
    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
