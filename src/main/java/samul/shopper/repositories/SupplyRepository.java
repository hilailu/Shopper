package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samul.shopper.entities.Supply;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
