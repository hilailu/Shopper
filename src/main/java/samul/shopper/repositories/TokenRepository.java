package samul.shopper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samul.shopper.entities.Token;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
