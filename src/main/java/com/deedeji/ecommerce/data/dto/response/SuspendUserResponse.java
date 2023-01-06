package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuspendUserResponse {
    private int code;
    private String message;
}
