package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PaymentResponse {
    private String message;
    private String status;
    private Data data;
}
