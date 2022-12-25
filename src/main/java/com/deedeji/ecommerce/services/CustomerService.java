package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.data.dto.response.CustomerRegisterResponse;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;

public interface CustomerService {

    CustomerRegisterResponse register(CustomerRegistrationRequest request) throws EcommerceExpressException;

    String updateCustomerProfile(UpdateCustomerDetails details) throws UserNotFoundException;
}
