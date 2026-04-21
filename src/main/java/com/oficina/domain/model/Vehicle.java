package com.oficina.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem dependências de framework.
 */
public class Vehicle {

    private UUID id;
    private UUID customerId;
    private String plate;
    private String brand;
    private String model;
    private Integer year;
    private Instant createdAt;

    public Vehicle() {}

    public Vehicle(UUID customerId, String plate) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.plate = plate;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
