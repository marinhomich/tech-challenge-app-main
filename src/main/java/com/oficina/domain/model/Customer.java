package com.oficina.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem dependências de framework.
 */
public class Customer {

    private UUID id;
    private String name;
    private String document;
    private String email;
    private String phone;
    private Instant createdAt;

    public Customer() {}

    public Customer(String name, String document) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.document = document;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
