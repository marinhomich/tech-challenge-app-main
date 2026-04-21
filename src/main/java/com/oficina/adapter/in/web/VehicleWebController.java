package com.oficina.adapter.in.web;

import com.oficina.domain.model.Vehicle;
import com.oficina.domain.port.in.VehicleUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "Gestão de Veículos")
public class VehicleWebController {

    private final VehicleUseCase vehicleUseCase;

    public VehicleWebController(VehicleUseCase vehicleUseCase) {
        this.vehicleUseCase = vehicleUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar veículo", description = "Cadastra um veículo com validação de placa")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Criado"), @ApiResponse(responseCode = "400", description = "Placa inválida")})
    public ResponseEntity<?> create(@RequestBody Vehicle v) {
        try {
            return ResponseEntity.ok(vehicleUseCase.create(v));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veículo por ID")
    public ResponseEntity<Vehicle> get(@PathVariable UUID id) {
        return vehicleUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar veículos")
    public ResponseEntity<List<Vehicle>> list() {
        return ResponseEntity.ok(vehicleUseCase.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar veículo")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Vehicle v) {
        try {
            return ResponseEntity.ok(vehicleUseCase.update(id, v));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover veículo")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vehicleUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
