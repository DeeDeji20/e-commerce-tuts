package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.PaymentRequest;
import com.deedeji.ecommerce.data.dto.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    private static PaymentResponse paymentResponse;
    @Test
    void testPay(){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("deolaoladeji@gmail.com");
        paymentRequest.setAmount("200");

        paymentResponse = paymentService.pay(paymentRequest);
        log.info(paymentResponse.getData().getAuthorization_url());
        assertThat(paymentResponse).isNotNull();
        assertThat(paymentResponse.getData().getAuthorization_url()).isNotNull();
    }

    @Test
    void testCompletePayment(){
        log.info("From test {}",paymentResponse.getData().getReference());
        paymentResponse = paymentService.completePayment(paymentResponse.getData().getReference());
        assertThat(paymentResponse.getData()).isNotNull();

    }
}