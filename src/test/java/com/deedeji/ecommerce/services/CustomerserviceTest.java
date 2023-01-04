package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.CustomerRegistrationRequest;
import com.deedeji.ecommerce.data.dto.request.UpdateCustomerDetails;
import com.deedeji.ecommerce.data.dto.response.CustomerRegisterResponse;
import com.deedeji.ecommerce.data.models.Customer;
import com.deedeji.ecommerce.exception.EcommerceExpressException;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import com.deedeji.ecommerce.util.EcommerceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    private CustomerRegistrationRequest request;

    @BeforeEach
    void setUp(){
        request = CustomerRegistrationRequest
                .builder()
                .email("test@gmail.com")
                .password("password")
                .country("Nigeria")
                .build();
    }

    @Test
    void registerCustomerTest() throws EcommerceExpressException {
        CustomerRegisterResponse customerRegisterResponse =
                customerService.register(request);
        assertThat(customerRegisterResponse).isNotNull();
        assertThat(customerRegisterResponse.getMessage()).isNotNull();
        assertThat(customerRegisterResponse.getUserId()).isGreaterThan(0);
        assertThat(customerRegisterResponse.getCode()).isEqualTo(201);
    }

    @Test
    void customerExist_throwsException() throws EcommerceExpressException {
        assertThrows(EcommerceExpressException.class, ()-> customerService.register(request));
    }

    @Test
    void updateProfileTest() throws EcommerceExpressException {
        var request = CustomerRegistrationRequest
                .builder()
                .email("test2@gmail.com")
                .password("password")
                .country("Nigeria")
                .build();
        CustomerRegisterResponse customerRegistrationResponse=
                customerService.register(request);
        assertThat(customerRegistrationResponse).isNotNull();
        UpdateCustomerDetails details = UpdateCustomerDetails
                .builder()
                .customerId(customerRegistrationResponse.getUserId())
                .imageUrl(EcommerceUtils.getMockCloudinaryImageUrl())
                .lastName("test LastName")
                .city("test city")
                .street("test street")
                .state("Lagos")
                .buildingNumber(312)
                .phoneNumber("9999999999")
                .build();
        String updateResponse = customerService
                .updateCustomerProfile(details);
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.contains("success")).isTrue();
    }

    @Test
    void getAllCustomers(){
        var allCustomers = customerService.getAllCustomers();
        assertThat(allCustomers).isNotNull();
    }

    @Test
    void getCustomerById() throws UserNotFoundException {
        var customer = customerService.findById(1L);
        assertThat(customer.get().getFirstName()).isEqualTo(request.getFirstName());
    }

    @Test
    void getCustomerByInvalidId_throwsException() {
        assertThrows(UserNotFoundException.class, ()-> customerService.findById(10L));
    }
}
