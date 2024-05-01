package samul.shopper.mappers;

import samul.shopper.dtos.CustomerDto;
import samul.shopper.entities.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getCustomerName(),
                customer.getPhoneNumber(),
                customer.getUser()
        );
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getId(),
                customerDto.getCustomerName(),
                customerDto.getPhoneNumber(),
                customerDto.getUser()
        );
    }
}
