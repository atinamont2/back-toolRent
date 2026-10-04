package group3.tool.rent.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import group3.tool.rent.order.dto.OrderDTO;
import group3.tool.rent.order.dto.OrderRequestDTO;
import group3.tool.rent.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(Authentication authentication) {
        return ResponseEntity.ok(orderService.findAll(authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(orderService.findById(id, authentication));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO request, Authentication authentication) {
        OrderDTO savedOrder = orderService.createOrder(request, authentication);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(orderService.cancelOrder(id, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id, Authentication authentication) {
        orderService.deleteOrderById(id, authentication);

        return ResponseEntity.noContent().build();
    }
}
