package samul.shopper.services;

import samul.shopper.dtos.OrderRequestDto;
import samul.shopper.dtos.OrderStatusDto;
import samul.shopper.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDto orderRequest);
    List<Order> getOrdersForCustomer();
    List<Order> getAllOrders();
    void updateOrderStatus(OrderStatusDto orderStatusDto);
}
