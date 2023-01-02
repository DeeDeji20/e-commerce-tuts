package com.deedeji.ecommerce.data.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PaymentRequest {
    private final String key = "sk_test_b3b9265ee93837e73fe1abb549b3c631c35f1fe9";
    private String email;
    private String amount;
}
