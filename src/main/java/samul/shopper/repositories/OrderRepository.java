package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import samul.shopper.entities.Customer;
import samul.shopper.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
}
