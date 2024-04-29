package samul.shopper.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samul.shopper.dtos.AuthRequestDto;
import samul.shopper.dtos.JwtResponseDto;
import samul.shopper.dtos.LogoutRequestDto;
import samul.shopper.dtos.RefreshTokenRequestDto;
import samul.shopper.entities.Token;
import samul.shopper.services.JwtService;
import samul.shopper.services.TokenService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final int cookieExpiry = 24*60*60*1000;

    @PostMapping("/login")
    public JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            Token refreshToken = tokenService.createToken(authRequestDto.getLogin());
            String accessToken = jwtService.generateToken(authRequestDto.getLogin());
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LogoutRequestDto logoutRequestDto, HttpServletResponse response) {
        tokenService.removeToken(logoutRequestDto.getId());
        return "success i guess?";
    }


    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
        return tokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(tokenService::verifyExpiration)
                .map(Token::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getLogin());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
