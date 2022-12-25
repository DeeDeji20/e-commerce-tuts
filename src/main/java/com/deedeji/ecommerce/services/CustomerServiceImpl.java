package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.data.dto.response.CustomerRegisterResponse;
import com.deedeji.ecommerce.data.models.Address;
import com.deedeji.ecommerce.data.models.Authority;
import com.deedeji.ecommerce.data.models.Cart;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public CustomerRegisterResponse register(CustomerRegistrationRequest request) throws EcommerceExpressException {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(request.getEmail());
        if (foundCustomer.isPresent()) throw new EcommerceExpressException(
                String.format("Email %s already exist", request.getEmail())
        );

        Customer customer = mapper.map(request, Customer.class);
        customer.setCart(new Cart());
        setCustomerAddress(request, customer);
        customer.getAuthorities().add(Authority.BUY);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer saved in db::{}", savedCustomer);
        var token = verificationTokenService.createToken(savedCustomer.getEmail());

        return registrationCustomerBuilder(savedCustomer);
    }

    @Override
    public String updateCustomerProfile(UpdateCustomerDetails details) throws UserNotFoundException {
        Customer customerToUpdate = customerRepository.findById(details.getCustomerId())
                .orElseThrow(()-> new UserNotFoundException(
                        String.format("Customer with id %d, not found", details.getCustomerId())
                ));
        mapper.map(details, customerToUpdate);

        return null;
    }

    private CustomerRegisterResponse registrationCustomerBuilder(Customer savedCustomer) {
        return CustomerRegisterResponse.builder()
                .message("Success")
                .userId(savedCustomer.getId())
                .code(201)
                .build();
    }

    private void setCustomerAddress(CustomerRegistrationRequest request, Customer customer) {
        Address customerAddress = new Address();
        customerAddress.setCountry(request.getCountry());
        customer.getAddress().add(customerAddress);
    }
}
