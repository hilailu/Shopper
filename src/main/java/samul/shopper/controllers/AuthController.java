package samul.shopper.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.AuthRequestDto;
import samul.shopper.dtos.JwtResponseDto;
import samul.shopper.dtos.UserDto;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.services.JwtService;
import samul.shopper.services.TokenService;
import samul.shopper.services.UserService;

@CrossOrigin("*")
@AllArgsConstructor
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

    @GetMapping("/")
    public String redirect(HttpServletRequest request) {
        if (jwtService.getAccessTokenFromCookie(request) != null) {
            return "/profile";
        } else {
            return "/auth/login";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> registerAndGetToken(@RequestBody UserDto userDto, HttpServletResponse response){
        String password = userDto.getPassword();
        userService.createUser(userDto);
        JwtResponseDto jwtResponseDto = authenticate(userDto.getLogin(), password, response);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        JwtResponseDto jwtResponseDto = authenticate(authRequestDto.getLogin(), authRequestDto.getPassword(), response);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    tokenService.revokeToken(cookie.getValue());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return ResponseEntity.ok("Logout successful");
                }
            }
        }
        return ResponseEntity.ok("Token not found");
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
