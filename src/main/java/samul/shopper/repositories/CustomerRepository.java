package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import samul.shopper.entities.Customer;
import samul.shopper.entities.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUser(User user);
}
