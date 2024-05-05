package samul.shopper.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.OrderStatusDto;
import samul.shopper.entities.Order;
import samul.shopper.services.OrderService;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/orders")
public class OrderStatusController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        try {
            List<Order> orders = orderService.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusDto orderStatusDto){
        try {
            orderService.updateOrderStatus(orderStatusDto);
            return ResponseEntity.ok("Order status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
