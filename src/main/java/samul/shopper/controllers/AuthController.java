package samul.shopper.controllers;

import jakarta.servlet.ServletException;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.AuthRequestDto;
import samul.shopper.dtos.JwtResponseDto;
import samul.shopper.dtos.UserDto;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.services.JwtService;
import samul.shopper.services.TokenService;
import samul.shopper.services.UserService;

import java.util.Objects;

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
    public String redirect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(auth.getName(), "anonymousUser")) {
            boolean admin = auth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN"));
            return admin ? "/admin/" : "/profile";
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
    public ResponseEntity<String> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        CookieClearingLogoutHandler cook = new CookieClearingLogoutHandler("accessToken");
        cook.logout(request, response, authentication);
        logoutHandler.logout(request, response, authentication);
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
