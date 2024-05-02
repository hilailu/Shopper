package samul.shopper.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.OrderRequestDto;
import samul.shopper.entities.Customer;
import samul.shopper.entities.Order;
import samul.shopper.services.OrderService;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequestDto orderRequest) {
        try {
            Order order = orderService.createOrder(orderRequest);
            return ResponseEntity.ok("Order created successfully with ID: " + order.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        try {
            List<Order> orders = orderService.getOrdersForCustomer();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }
}
