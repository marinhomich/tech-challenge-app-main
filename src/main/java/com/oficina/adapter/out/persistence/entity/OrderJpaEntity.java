package com.oficina.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_estimated")
    private BigDecimal totalEstimated;

    @Column(name = "total_final")
    private BigDecimal totalFinal;

    @Column(name = "approved")
    private boolean approved = false;

    @Column(name = "approval_rejected_reason")
    private String approvalRejectedReason;

    @Column(name = "received_at")
    private Instant receivedAt;

    @Column(name = "diagnostic_at")
    private Instant diagnosticAt;

    @Column(name = "awaiting_approval_at")
    private Instant awaitingApprovalAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "delivered_at")
    private Instant deliveredAt;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPartJpaEntity> parts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
        if (this.createdAt == null) this.createdAt = Instant.now();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getTotalEstimated() { return totalEstimated; }
    public void setTotalEstimated(BigDecimal totalEstimated) { this.totalEstimated = totalEstimated; }
    public BigDecimal getTotalFinal() { return totalFinal; }
    public void setTotalFinal(BigDecimal totalFinal) { this.totalFinal = totalFinal; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public String getApprovalRejectedReason() { return approvalRejectedReason; }
    public void setApprovalRejectedReason(String approvalRejectedReason) { this.approvalRejectedReason = approvalRejectedReason; }
    public Instant getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Instant receivedAt) { this.receivedAt = receivedAt; }
    public Instant getDiagnosticAt() { return diagnosticAt; }
    public void setDiagnosticAt(Instant diagnosticAt) { this.diagnosticAt = diagnosticAt; }
    public Instant getAwaitingApprovalAt() { return awaitingApprovalAt; }
    public void setAwaitingApprovalAt(Instant awaitingApprovalAt) { this.awaitingApprovalAt = awaitingApprovalAt; }
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public Instant getFinishedAt() { return finishedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt; }
    public Instant getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(Instant deliveredAt) { this.deliveredAt = deliveredAt; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Instant deletedAt) { this.deletedAt = deletedAt; }
    public List<OrderItemJpaEntity> getItems() { return items; }
    public void setItems(List<OrderItemJpaEntity> items) { this.items = items; }
    public List<OrderPartJpaEntity> getParts() { return parts; }
    public void setParts(List<OrderPartJpaEntity> parts) { this.parts = parts; }
}
