package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import samul.shopper.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findUserById(Long id);
}
