package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegisterResponse {
    private Long userId;
    private String message;
    private int code;
}
