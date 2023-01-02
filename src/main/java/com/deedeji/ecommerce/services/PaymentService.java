package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.PaymentRequest;
import com.deedeji.ecommerce.data.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(PaymentRequest paymentRequest);
    PaymentResponse completePayment(String paymentReference);
}
