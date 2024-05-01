package samul.shopper.services;

import samul.shopper.dtos.CustomerDto;
import samul.shopper.entities.Customer;

public interface CustomerService {
    CustomerDto updateCustomer(CustomerDto customerDto);
    Customer findByUserLogin(String login);
}
