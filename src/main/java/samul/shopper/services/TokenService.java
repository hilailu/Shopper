package samul.shopper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samul.shopper.entities.Token;
import samul.shopper.entities.User;
import samul.shopper.repositories.TokenRepository;
import samul.shopper.repositories.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    public void saveToken(String login, String generatedToken){
        User user = userRepository.findByLogin(login);
        Token token = Token.builder()
                .user(user)
                .token(generatedToken)
                .expiryDate(new Date((new Date()).getTime() + 24*60*60*1000).toInstant())
                .build();
        revokeToken(user.getId());
        tokenRepository.save(token);
    }

    public void revokeToken(Long id) {
        User user = userRepository.findUserById(id);
        Optional<Token> token = tokenRepository.findByUser(user);
        token.ifPresent(value -> tokenRepository.delete(value));
    }
}
