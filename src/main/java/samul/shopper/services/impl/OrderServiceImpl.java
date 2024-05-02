package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.OrderProductRequest;
import samul.shopper.dtos.OrderRequestDto;
import samul.shopper.dtos.ProductDto;
import samul.shopper.entities.Customer;
import samul.shopper.entities.Order;
import samul.shopper.entities.OrderProduct;
import samul.shopper.entities.Product;
import samul.shopper.repositories.CustomerRepository;
import samul.shopper.repositories.OrderProductRepository;
import samul.shopper.repositories.OrderRepository;
import samul.shopper.repositories.UserRepository;
import samul.shopper.services.CustomerService;
import samul.shopper.services.OrderService;
import samul.shopper.services.ProductService;
import samul.shopper.services.UserService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderProductRepository orderProductRepository;
    private OrderRepository orderRepository;
    private ProductService productService;
    private CustomerService customerService;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;

    public Order createOrder(OrderRequestDto orderRequest) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUserLogin(login);
        if (customer == null)
        {
            customer = new Customer();
            customer.setUser(userRepository.findByLogin(login));
            customer.setCustomerName("anonymous");
            customer.setPhoneNumber("phone");
            customer = customerRepository.save(customer);
        }

        Order order = new Order();
        order.setOrderStatus("Pending");
        order.setDate(new Date());
        order.setTotalPrice(calculateTotalPrice(orderRequest.getOrderProducts()));
        order.setCustomer(customer);
        order = orderRepository.save(order);

        for (OrderProductRequest orderProductRequest : orderRequest.getOrderProducts()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(orderProductRequest.getProductId());
            orderProduct.setAmount(orderProductRequest.getQuantity());
            orderProductRepository.save(orderProduct);
        }

        return order;
    }

    @Override
    public List<Order> getOrdersForCustomer() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUserLogin(login);
        return orderRepository.findByCustomer(customer);
    }

    private double calculateTotalPrice(List<OrderProductRequest> orderProducts) {
        double totalPrice = 0;
        for (OrderProductRequest orderProduct : orderProducts) {
            ProductDto product = productService.getProductById(orderProduct.getProductId());
            totalPrice += product.getPrice() * orderProduct.getQuantity();
        }
        return totalPrice;
    }
}
