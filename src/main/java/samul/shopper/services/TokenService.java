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

    public Token createToken(String username){
        Token refreshToken = Token.builder()
                .user(userRepository.findByLogin(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(new Date((new Date()).getTime() + 24*60*60*1000).toInstant())
                .build();
        return tokenRepository.save(refreshToken);
    }

    public void removeToken(Long id) {
        User user = userRepository.findUserById(id);
        Token token = tokenRepository.findByUser(user).orElseThrow();
        removeTokenFromDb(token);
    }

    public Optional<Token> findByToken(String token){
        return tokenRepository.findByToken(token);
    }

    public Token verifyExpiration(Token token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            removeTokenFromDb(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    private void removeTokenFromDb(Token token) {
        tokenRepository.delete(token);
    }
}
