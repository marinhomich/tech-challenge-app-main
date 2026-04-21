package com.oficina.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem dependências de framework ou infraestrutura.
 */
public class Order {

    private UUID id;
    private String number;
    private UUID customerId;
    private UUID vehicleId;
    private String status;
    private BigDecimal totalEstimated;
    private BigDecimal totalFinal;
    private boolean approved;
    private String approvalRejectedReason;
    private Instant receivedAt;
    private Instant diagnosticAt;
    private Instant awaitingApprovalAt;
    private Instant startedAt;
    private Instant finishedAt;
    private Instant deliveredAt;
    private Instant createdAt;
    private Instant deletedAt;
    private List<OrderItem> items = new ArrayList<>();
    private List<OrderPart> parts = new ArrayList<>();

    public Order() {}

    public Order(String number, UUID customerId, UUID vehicleId) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.status = OrderStatus.RECEIVED.name();
        this.createdAt = Instant.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public boolean isFinished() {
        return OrderStatus.FINISHED.name().equals(status) || OrderStatus.DELIVERED.name().equals(status);
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
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public List<OrderPart> getParts() { return parts; }
    public void setParts(List<OrderPart> parts) { this.parts = parts; }
}
