package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import samul.shopper.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
