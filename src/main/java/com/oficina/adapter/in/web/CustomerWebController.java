package com.oficina.adapter.in.web;

import com.oficina.domain.model.Customer;
import com.oficina.domain.port.in.CustomerUseCase;
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
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Gestão de Clientes")
public class CustomerWebController {

    private final CustomerUseCase customerUseCase;

    public CustomerWebController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar cliente", description = "Cadastra um novo cliente com validação de CPF/CNPJ")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Criado"), @ApiResponse(responseCode = "400", description = "CPF/CNPJ inválido")})
    public ResponseEntity<?> create(@RequestBody Customer c) {
        try {
            return ResponseEntity.ok(customerUseCase.create(c));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<Customer> get(@PathVariable UUID id) {
        return customerUseCase.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<Customer>> list() {
        return ResponseEntity.ok(customerUseCase.findAll());
    }

    @GetMapping("/by-document")
    @Operation(summary = "Buscar cliente por CPF/CNPJ", description = "Localiza um cliente pelo número de CPF ou CNPJ")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Encontrado"), @ApiResponse(responseCode = "400", description = "Documento inválido"), @ApiResponse(responseCode = "404", description = "Não encontrado")})
    public ResponseEntity<?> getByDocument(@RequestParam String document) {
        try {
            return customerUseCase.findByDocument(document)
                    .map(c -> ResponseEntity.ok((Object) c))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar cliente")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Customer c) {
        try {
            return ResponseEntity.ok(customerUseCase.update(id, c));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover cliente")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
