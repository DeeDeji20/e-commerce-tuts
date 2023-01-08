package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRegisterResponse {
    private Long userId;
    private String message;
    private int code;
}
