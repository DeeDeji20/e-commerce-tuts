package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateProductResponse {
    private String message;
    private int statusCode;
    private String productName;
    private String description;
    private BigDecimal price;
}
