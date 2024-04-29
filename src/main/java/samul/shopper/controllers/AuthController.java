package samul.shopper.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import samul.shopper.exceptions.ResourceNotFoundException;
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
            String accessToken = jwtService.generateToken(authRequestDto.getLogin());
            tokenService.saveToken(authRequestDto.getLogin(), accessToken);
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return JwtResponseDto.builder()
                    .accessToken(accessToken)
                    .build();

        } else {
            throw new ResourceNotFoundException("Something went wrong");
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        tokenService.revokeToken(logoutRequestDto.getId());
        return "success i guess?";
    }
}
