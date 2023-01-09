package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.response.SuspendUserResponse;
import com.deedeji.ecommerce.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void suspendCustomerTest() throws UserNotFoundException {
        SuspendUserResponse res = adminService.suspendUser("deolaoladeji@gmail.com");
        assertThat(res).isNotNull();
        assertThat(res.getCode()).isEqualTo(200);
    }
}
