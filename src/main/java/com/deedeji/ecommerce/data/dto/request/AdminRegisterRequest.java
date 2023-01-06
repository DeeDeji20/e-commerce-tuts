package com.deedeji.ecommerce.data.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterRequest {
    @NotNull(message = "Country field is required")
    @NotEmpty(message = "Country field is required")
    private String country;

    @Email(message = "Invalid email")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    private String firstName;
    @NotNull(message = "Password field cannot be null")
    @NotEmpty(message = "Password field cannot be empty")
    private String password;
}
