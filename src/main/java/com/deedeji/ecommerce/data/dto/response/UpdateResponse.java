package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateResponse {
    private int code;
    private String message;
}
