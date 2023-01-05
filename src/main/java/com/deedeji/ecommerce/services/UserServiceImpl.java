package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.LoginRequest;
import com.deedeji.ecommerce.data.dto.response.LoginResponse;
import com.deedeji.ecommerce.data.models.Admin;
import com.deedeji.ecommerce.data.models.AppUser;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.data.models.Vendor;
import com.deedeji.ecommerce.data.repository.AdminRepository;
import com.deedeji.ecommerce.data.repository.CustomerRepository;
import com.deedeji.ecommerce.data.repository.VendorRepository;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    private final VendorRepository vendorRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("Got here");
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
        Optional<Vendor> vendor = vendorRepository.findByEmail(loginRequest.getEmail());
        if(vendor.isPresent() && vendor.get()
                .getPassword().equals(loginRequest.getPassword())){
            return LoginResponse.builder()
                    .message("user logged in successfully")
                    .code(200)
                    .build();
        }
        return LoginResponse.builder()
                .message("Login failed, Bad credentials")
                .code(200)
                .build();
    }

    @Override
    public AppUser getUserByUsername(String email) throws UserNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()){
            return customer.get();
        }
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()){
            return admin.get();
        }
        Optional<Vendor> vendor = vendorRepository.findByEmail(email);
        if(vendor.isPresent()){
            return vendor.get();
        }
        throw new UserNotFoundException("User does not exist");
    }
}
