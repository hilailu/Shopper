package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.CustomerDto;
import samul.shopper.entities.Customer;
import samul.shopper.entities.User;
import samul.shopper.mappers.CustomerMapper;
import samul.shopper.repositories.CustomerRepository;
import samul.shopper.repositories.UserRepository;
import samul.shopper.services.CustomerService;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private UserRepository userRepository;
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        User customerUser = customerDto.getUser();
        Customer customer = customerRepository.findByUser(customerUser);
        User savedUser = userRepository.findByLogin(customerUser.getLogin());

        savedUser.setLogin(customerUser.getLogin());
        savedUser.setEmail(customerUser.getEmail());
        userRepository.save(savedUser);

        customer.setUser(savedUser);
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public Customer findByUserLogin(String login) {
        User user = userRepository.findByLogin(login);
        return customerRepository.findByUser(user);
    }
}
