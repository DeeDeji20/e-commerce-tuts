package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public SuspendUserResponse suspendCustomer(Long id) throws UserNotFoundException {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(String.format(
                        "User with the id %d not available", id)));

        foundCustomer.setEnabled(false);
        customerRepository.save(foundCustomer);
        return SuspendUserResponse.builder()
                .code(200)
                .message(String.format("%s's details suspended successfully", foundCustomer.getFirstName()))
                .build();
    }

}
