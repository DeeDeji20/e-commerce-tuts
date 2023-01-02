package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.PaymentRequest;
import com.deedeji.ecommerce.data.dto.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    @Override
    public PaymentResponse pay(PaymentRequest paymentRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Payment key::: "+ paymentRequest.getKey());
        headers.set("Authorization", "Bearer "+ paymentRequest.getKey());
        HttpEntity<PaymentRequest> httpEntity = new HttpEntity<>(paymentRequest, headers);
        var response = restTemplate.postForEntity("https://api.paystack.co/transaction/initialize", httpEntity, PaymentResponse.class);
        return response.getBody();
    }

    @Override
    public PaymentResponse completePayment(String paymentReference) {
        log.info("Reference---> {}", paymentReference);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.paystack.co/transaction/verify/:" + paymentReference;
        log.info("Url---> {}", url);
        String key =  "sk_test_b3b9265ee93837e73fe1abb549b3c631c35f1fe9";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+key);
        HttpEntity<PaymentRequest> request = new HttpEntity<>(null, headers);
        var response = restTemplate.exchange(url, HttpMethod.GET, request, PaymentResponse.class);
        return response.getBody();
    }
}
