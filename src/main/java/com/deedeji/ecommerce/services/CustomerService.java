package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.data.dto.response.CustomerRegisterResponse;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerRegisterResponse register(CustomerRegistrationRequest request) throws EcommerceExpressException;

    String updateCustomerProfile(UpdateCustomerDetails details) throws UserNotFoundException;

    List<Customer> getAllCustomers();

    Optional<Customer> findById(Long id) throws UserNotFoundException;

    String suspendCustomer(Long id) throws UserNotFoundException;
}
