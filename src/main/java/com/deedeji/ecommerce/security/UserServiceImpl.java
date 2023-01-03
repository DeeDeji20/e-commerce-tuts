package com.deedeji.ecommerce.security;

import com.deedeji.ecommerce.data.dto.request.LoginRequest;
import com.deedeji.ecommerce.data.dto.response.LoginResponse;
import com.deedeji.ecommerce.data.models.Admin;
import com.deedeji.ecommerce.data.models.AppUser;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.data.models.Vendor;
import com.deedeji.ecommerce.data.repository.AdminRepository;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Customer> customer = customerRepository.findByEmail(loginRequest.getEmail());
        if (customer.isPresent() && customer.get().getPassword()
                .equals(loginRequest.getPassword())){
            return LoginResponse.builder()
                    .message("user logged in successfully")
                    .code(200)
                    .build();
        }
        Optional<Admin> admin = adminRepository.findByEmail(loginRequest.getEmail());
        if (admin.isPresent() && admin.get().getPassword()
                .equals(loginRequest.getPassword())){
            return LoginResponse.builder()
                    .message("user logged in successfully")
                    .code(200)
                    .build();
        }
        Optional<Vendor>
        if()
        return null;
    }

    @Override
    public AppUser getUserByUsername(String email) {
        return null;
    }
}
