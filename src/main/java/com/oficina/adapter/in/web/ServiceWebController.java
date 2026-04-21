package com.oficina.adapter.in.web;

import com.oficina.domain.model.ServiceItem;
import com.oficina.domain.port.in.ServiceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
@Tag(name = "Services", description = "Gestão de Serviços")
public class ServiceWebController {

    private final ServiceUseCase serviceUseCase;

    public ServiceWebController(ServiceUseCase serviceUseCase) {
        this.serviceUseCase = serviceUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar serviço")
    public ResponseEntity<ServiceItem> create(@RequestBody ServiceItem s) {
        return ResponseEntity.ok(serviceUseCase.create(s));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID")
    public ResponseEntity<ServiceItem> get(@PathVariable UUID id) {
        return serviceUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar serviços")
    public ResponseEntity<List<ServiceItem>> list() {
        return ResponseEntity.ok(serviceUseCase.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar serviço")
    public ResponseEntity<ServiceItem> update(@PathVariable UUID id, @RequestBody ServiceItem s) {
        try {
            return ResponseEntity.ok(serviceUseCase.update(id, s));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover serviço")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        serviceUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
