package com.deedeji.ecommerce.data.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateAdminProfileRequest {
    private Long adminId;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String imageUrl;
    private int buildingNumber;
    private String street;
    private String city;
    private String state;
}
