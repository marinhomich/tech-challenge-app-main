package com.oficina.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "services")
public class ServiceItemJpaEntity {

    @Id
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Column(name = "active")
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public BigDecimal getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(BigDecimal estimatedHours) { this.estimatedHours = estimatedHours; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
