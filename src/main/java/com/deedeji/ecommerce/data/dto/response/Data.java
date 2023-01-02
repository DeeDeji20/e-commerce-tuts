package com.deedeji.ecommerce.data.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Data {
    private String authorization_url;
    private String access_code;
    private String reference;
}
