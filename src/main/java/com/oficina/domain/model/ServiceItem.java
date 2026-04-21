package com.oficina.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem dependências de framework.
 */
public class ServiceItem {

    private UUID id;
    private String code;
    private String description;
    private BigDecimal hourlyRate;
    private BigDecimal estimatedHours;
    private boolean active;

    public ServiceItem() {}

    public ServiceItem(String code, String description) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.description = description;
        this.active = true;
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
