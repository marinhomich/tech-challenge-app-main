package com.oficina.adapter.in.web;

import com.oficina.domain.model.Part;
import com.oficina.domain.port.in.PartUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parts")
@Tag(name = "Parts", description = "Gestão de Peças e Insumos")
public class PartWebController {

    private final PartUseCase partUseCase;

    public PartWebController(PartUseCase partUseCase) {
        this.partUseCase = partUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar peça/insumo")
    public ResponseEntity<Part> create(@RequestBody Part part) {
        return ResponseEntity.ok(partUseCase.save(part));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar peça por ID")
    public ResponseEntity<Part> get(@PathVariable UUID id) {
        return partUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar peças/insumos")
    public ResponseEntity<List<Part>> list() {
        return ResponseEntity.ok(partUseCase.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar peça/insumo")
    public ResponseEntity<Part> update(@PathVariable UUID id, @RequestBody Part part) {
        return partUseCase.findById(id).map(existing -> {
            if (part.getSku() != null) existing.setSku(part.getSku());
            if (part.getName() != null) existing.setName(part.getName());
            if (part.getCostPrice() != null) existing.setCostPrice(part.getCostPrice());
            if (part.getSalePrice() != null) existing.setSalePrice(part.getSalePrice());
            if (part.getMinStock() != null) existing.setMinStock(part.getMinStock());
            return ResponseEntity.ok(partUseCase.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover peça/insumo")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        partUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/adjust")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ajustar estoque", description = "Adiciona (delta > 0) ou remove (delta < 0) unidades do estoque")
    public ResponseEntity<Part> adjust(@PathVariable UUID id, @RequestParam int delta,
                                       @RequestParam(required = false) UUID userId) {
        try {
            return ResponseEntity.ok(partUseCase.adjustStock(id, delta, userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
