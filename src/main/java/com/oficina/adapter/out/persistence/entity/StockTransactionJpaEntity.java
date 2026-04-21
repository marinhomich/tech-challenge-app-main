package com.oficina.adapter.out.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stock_transactions")
public class StockTransactionJpaEntity {

    @Id
    private UUID id;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "user_id")
    private UUID userId;

    public StockTransactionJpaEntity() {
    }

    public StockTransactionJpaEntity(UUID partId, UUID orderId, String type, Integer qty, UUID userId) {
        this.id = UUID.randomUUID();
        this.partId = partId;
        this.orderId = orderId;
        this.type = type;
        this.qty = qty;
        this.userId = userId;
        this.createdAt = Instant.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
        if (this.createdAt == null) this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
}
