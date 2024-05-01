package samul.shopper.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.CustomerDto;
import samul.shopper.entities.Customer;
import samul.shopper.mappers.CustomerMapper;
import samul.shopper.repositories.UserRepository;
import samul.shopper.services.CustomerService;
import samul.shopper.services.JwtService;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private UserRepository userRepository;
    private JwtService jwtService;
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomer(HttpServletRequest request) {
        String token = jwtService.getAccessTokenFromCookie(request);
        String login = jwtService.extractLogin(token);
        Customer customer = customerService.findByUserLogin(login);

        if (customer == null) {
            customer = new Customer();
            customer.setUser(userRepository.findByLogin(login));
        }

        return ResponseEntity.ok(CustomerMapper.mapToCustomerDto(customer));
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(customerDto);
        return ResponseEntity.ok(customerDto);
    }

}
