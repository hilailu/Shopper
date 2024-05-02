package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import samul.shopper.entities.OrderProduct;
import samul.shopper.helpers.OrderProductId;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
