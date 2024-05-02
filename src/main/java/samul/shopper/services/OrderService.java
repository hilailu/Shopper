package samul.shopper.services;

import samul.shopper.dtos.OrderRequestDto;
import samul.shopper.entities.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDto orderRequest);
    List<Order> getOrdersForCustomer();
}
