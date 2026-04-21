package com.oficina.adapter.in.web;

import com.oficina.adapter.in.web.dto.ApprovalRequestDto;
import com.oficina.adapter.in.web.dto.OrderCreateDto;
import com.oficina.adapter.in.web.dto.OrderItemDto;
import com.oficina.adapter.in.web.dto.OrderPartDto;
import com.oficina.domain.model.Order;
import com.oficina.domain.model.OrderItem;
import com.oficina.domain.model.OrderPart;
import com.oficina.domain.port.in.OrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Gestão de Ordens de Serviço")
public class OrderWebController {

    private final OrderUseCase orderUseCase;

    public OrderWebController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar OS", description = "Inicia uma Ordem de Serviço em rascunho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OS criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Order> create(@Valid @RequestBody OrderCreateDto dto) {
        String number = "OS-" + System.currentTimeMillis();
        Order order = new Order(number, dto.getCustomerId(), dto.getVehicleId());
        List<OrderItem> items = new ArrayList<>();
        if (dto.getItems() != null) {
            for (OrderItemDto it : dto.getItems()) {
                items.add(new OrderItem(it.getServiceId(), it.getQuantity(), it.getUnitPrice()));
            }
        }
        List<OrderPart> parts = new ArrayList<>();
        if (dto.getParts() != null) {
            for (OrderPartDto p : dto.getParts()) {
                OrderPart op = new OrderPart(p.getPartId(), p.getQuantity(), p.getUnitCost());
                op.setReserved(true);
                parts.add(op);
            }
        }
        order.setItems(items);
        order.setParts(parts);
        return ResponseEntity.ok(orderUseCase.createOrder(order));
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Consultar status da OS", description = "Informa a situação atual da OS — endpoint público, sem autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "OS não encontrada")
    })
    public ResponseEntity<Map<String, Object>> getStatus(@PathVariable UUID id) {
        return orderUseCase.findById(id).map(order -> {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("id", order.getId());
            result.put("number", order.getNumber());
            result.put("status", order.getStatus());
            result.put("approved", order.isApproved());
            if (order.getApprovalRejectedReason() != null) {
                result.put("rejectedReason", order.getApprovalRejectedReason());
            }
            return ResponseEntity.ok(result);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar OS por ID")
    public ResponseEntity<Order> get(@PathVariable UUID id) {
        return orderUseCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar ordens de serviço ativas",
            description = "Retorna ordens ordenadas por status (Em Execução > Aguardando Aprovação > Diagnóstico > Recebida) e por data. Exclui FINISHED e DELIVERED. Use ?all=true para incluí-las.")
    public ResponseEntity<List<Order>> list(@RequestParam(defaultValue = "false") boolean all) {
        List<Order> result = all ? orderUseCase.findAll() : orderUseCase.findAllActive();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar OS")
    public ResponseEntity<Order> update(@PathVariable UUID id, @Valid @RequestBody OrderCreateDto dto) {
        return orderUseCase.findById(id).map(existing -> {
            if (dto.getCustomerId() != null) existing.setCustomerId(dto.getCustomerId());
            if (dto.getVehicleId() != null) existing.setVehicleId(dto.getVehicleId());
            return ResponseEntity.ok(orderUseCase.updateOrder(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover OS")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        orderUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Notificação de aprovação/recusa do orçamento",
            description = "Recebe notificação externa de aprovação ou recusa do orçamento pelo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Estoque insuficiente ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "OS não encontrada")
    })
    public ResponseEntity<Order> approve(@PathVariable UUID id, @Valid @RequestBody ApprovalRequestDto dto) {
        Order o = orderUseCase.approveQuote(id, dto.isApproved(), dto.getReason());
        return ResponseEntity.ok(o);
    }

    @PostMapping("/{id}/diagnostic")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Iniciar diagnóstico")
    public ResponseEntity<Order> startDiagnostic(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.startDiagnostic(id));
    }

    @PostMapping("/{id}/send-to-approval")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Enviar para aprovação")
    public ResponseEntity<Order> sendToApproval(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.sendToApproval(id));
    }

    @PostMapping("/{id}/execute-service")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Iniciar execução do serviço")
    public ResponseEntity<Order> startExecution(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.startExecution(id));
    }

    @PostMapping("/{id}/finish")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Finalizar OS")
    public ResponseEntity<Order> finishOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.finishOrder(id));
    }

    @PostMapping("/{id}/execute-payment")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar pagamento da placa-chefe")
    public ResponseEntity<Order> deliverOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderUseCase.deliverOrder(id));
    }

    @PostMapping("/{id}/notify-status")
    @Operation(summary = "Notificar cliente sobre status da OS",
            description = "Envia notificação (e-mail) ao cliente com o status atual da OS")
    public ResponseEntity<Map<String, String>> notifyStatus(@PathVariable UUID id) {
        orderUseCase.notifyStatus(id);
        return ResponseEntity.ok(Map.of("message", "Notificação enviada com sucesso"));
    }

    @GetMapping("/stats/avg-execution-time")
    @Operation(summary = "Tempo médio de execução", description = "Retorna o tempo médio de execução das OS finalizadas, em segundos e minutos")
    public ResponseEntity<Map<String, Object>> avgExecutionTime() {
        Double avgSeconds = orderUseCase.getAverageExecutionTimeSeconds();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("averageExecutionTimeSeconds", avgSeconds);
        result.put("averageExecutionTimeMinutes", avgSeconds / 60.0);
        return ResponseEntity.ok(result);
    }
}
