package com.deedeji.ecommerce.data.dto.response;

import com.deedeji.ecommerce.data.models.Cart;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String message;
    private Cart cart;
}
