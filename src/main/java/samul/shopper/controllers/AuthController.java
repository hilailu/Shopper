package samul.shopper.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samul.shopper.dtos.AuthRequestDto;
import samul.shopper.dtos.JwtResponseDto;
import samul.shopper.dtos.LogoutRequestDto;
import samul.shopper.dtos.UserDto;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.services.JwtService;
import samul.shopper.services.TokenService;
import samul.shopper.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final int cookieExpiry = 24*60*60*1000;

    @PostMapping("/register")
    public JwtResponseDto registerAndGetToken(@RequestBody UserDto userDto, HttpServletResponse response){
        String password = userDto.getPassword();
        userService.createUser(userDto);
        return authenticate(userDto.getLogin(), password, response);
    }

    @PostMapping("/login")
    public JwtResponseDto authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        return authenticate(authRequestDto.getLogin(), authRequestDto.getPassword(), response);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    tokenService.revokeToken(cookie.getValue());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return "success";
                }
            }
        }
        return "No token found";
    }

    private JwtResponseDto authenticate(String login, String password, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        if(authentication.isAuthenticated()){
            String accessToken = jwtService.generateToken(login);
            tokenService.saveToken(login, accessToken);
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
}
