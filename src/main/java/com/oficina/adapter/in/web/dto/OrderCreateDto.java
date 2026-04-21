package com.oficina.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class OrderCreateDto {

    @NotNull(message = "customerId é obrigatório")
    private UUID customerId;

    @NotNull(message = "vehicleId é obrigatório")
    private UUID vehicleId;

    @Valid
    private List<OrderItemDto> items;

    @Valid
    private List<OrderPartDto> parts;

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }
    public List<OrderItemDto> getItems() { return items; }
    public void setItems(List<OrderItemDto> items) { this.items = items; }
    public List<OrderPartDto> getParts() { return parts; }
    public void setParts(List<OrderPartDto> parts) { this.parts = parts; }
}
