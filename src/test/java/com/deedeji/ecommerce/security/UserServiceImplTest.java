package com.deedeji.ecommerce.security;

import com.deedeji.ecommerce.data.dto.request.LoginRequest;
import com.deedeji.ecommerce.data.dto.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = LoginRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

    }

    @Test
    void loginUserTest(){
        var response = userService.login(loginRequest);
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(200);
    }
}