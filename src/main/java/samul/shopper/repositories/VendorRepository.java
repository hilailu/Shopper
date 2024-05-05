package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samul.shopper.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
