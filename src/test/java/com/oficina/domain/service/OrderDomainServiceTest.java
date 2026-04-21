package com.oficina.domain.service;

import com.oficina.domain.model.Customer;
import com.oficina.domain.model.Order;
import com.oficina.domain.model.OrderItem;
import com.oficina.domain.model.OrderPart;
import com.oficina.domain.model.Part;
import com.oficina.domain.port.out.CustomerRepositoryPort;
import com.oficina.domain.port.out.NotificationPort;
import com.oficina.domain.port.out.OrderRepositoryPort;
import com.oficina.domain.port.out.PartRepositoryPort;
import com.oficina.domain.port.out.StockTransactionPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderDomainServiceTest {

    @Mock
    private OrderRepositoryPort orderRepository;

    @Mock
    private PartRepositoryPort partRepository;

    @Mock
    private StockTransactionPort stockTransactionPort;

    @Mock
    private CustomerRepositoryPort customerRepository;

    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private OrderDomainService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldCalculateTotalAndSetStatus() {
        Order order = new Order();
        OrderItem item = new OrderItem();
        item.setUnitPrice(new BigDecimal("100"));
        item.setQuantity(new BigDecimal("2"));
        order.setItems(Collections.singletonList(item));

        OrderPart part = new OrderPart();
        part.setTotalCost(new BigDecimal("50"));
        order.setParts(Collections.singletonList(part));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.createOrder(order);

        assertEquals("RECEIVED", result.getStatus());
        assertNotNull(result.getReceivedAt());
        assertEquals(new BigDecimal("250"), result.getTotalEstimated());
        assertEquals(new BigDecimal("200"), item.getTotalPrice());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void approveQuote_approved_shouldDecrementStock() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        OrderPart op = new OrderPart();
        op.setPartId(UUID.randomUUID());
        op.setQuantity(2);
        order.setParts(Collections.singletonList(op));

        Part part = new Part();
        part.setStockQty(5);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(partRepository.findById(op.getPartId())).thenReturn(Optional.of(part));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.approveQuote(orderId, true, null);

        assertTrue(result.isApproved());
        assertEquals(3, part.getStockQty());
        assertFalse(op.isReserved());
        verify(partRepository, times(1)).save(part);
        verify(stockTransactionPort, times(1)).recordConsumption(op.getPartId(), orderId, 2);
    }

    @Test
    void approveQuote_approvedInsufficientStock_shouldThrowException() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        OrderPart op = new OrderPart();
        op.setPartId(UUID.randomUUID());
        op.setQuantity(2);
        order.setParts(Collections.singletonList(op));

        Part part = new Part();
        part.setStockQty(1);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(partRepository.findById(op.getPartId())).thenReturn(Optional.of(part));

        assertThrows(IllegalStateException.class, () -> service.approveQuote(orderId, true, null));
        verify(partRepository, never()).save(any());
    }

    @Test
    void approveQuote_rejected_shouldUpdateStatusAndReason() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.approveQuote(orderId, false, "Too expensive");

        assertFalse(result.isApproved());
        assertEquals("RECEIVED", result.getStatus());
        assertEquals("Too expensive", result.getApprovalRejectedReason());
    }

    @Test
    void startDiagnostic_shouldUpdateStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.startDiagnostic(orderId);

        assertEquals("IN_DIAGNOSTIC", result.getStatus());
        assertNotNull(result.getDiagnosticAt());
    }

    @Test
    void sendToApproval_shouldUpdateStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.sendToApproval(orderId);

        assertEquals("AWAITING_APPROVAL", result.getStatus());
        assertNotNull(result.getAwaitingApprovalAt());
    }

    @Test
    void startExecution_approved_shouldUpdateStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setApproved(true);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = service.startExecution(orderId);

        assertEquals("IN_EXECUTION", result.getStatus());
        assertNotNull(result.getStartedAt());
    }

    @Test
    void startExecution_notApproved_shouldThrowException() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setApproved(false);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> service.startExecution(orderId));
    }

    @Test
    void finishOrder_and_deliverOrder_shouldUpdateStatus() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order finished = service.finishOrder(orderId);
        assertEquals("FINISHED", finished.getStatus());

        Order delivered = service.deliverOrder(orderId);
        assertEquals("DELIVERED", delivered.getStatus());
    }

    @Test
    void findAll_and_findAllActive_shouldReturnExpected() {
        Order o1 = new Order();
        o1.setStatus("FINISHED"); // lower priority or logic defined in enum
        when(orderRepository.findAllActive()).thenReturn(Collections.singletonList(o1));
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(o1));

        List<Order> active = service.findAllActive();
        assertEquals(1, active.size());

        List<Order> all = service.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void getAverageExecutionTimeSeconds_shouldComputeProperly() {
        Order o1 = new Order();
        o1.setStartedAt(Instant.ofEpochSecond(1000));
        o1.setFinishedAt(Instant.ofEpochSecond(1100)); // 100 seconds
        
        Order o2 = new Order(); // no started/finished

        Order o3 = new Order();
        o3.setStartedAt(Instant.ofEpochSecond(2000));
        o3.setFinishedAt(Instant.ofEpochSecond(2200)); // 200 seconds

        when(orderRepository.findAll()).thenReturn(List.of(o1, o2, o3));

        Double avg = service.getAverageExecutionTimeSeconds();
        assertEquals(150.0, avg);
    }

    @Test
    void notifyStatus_shouldCallNotificationPort() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setCustomerId(UUID.randomUUID());

        Customer customer = new Customer();
        
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(customerRepository.findById(order.getCustomerId())).thenReturn(Optional.of(customer));

        service.notifyStatus(orderId);

        verify(notificationPort, times(1)).notifyOrderStatus(order, customer);
    }
}
