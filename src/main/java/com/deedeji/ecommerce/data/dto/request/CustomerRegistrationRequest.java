package com.deedeji.ecommerce.data.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Validated
public class CustomerRegistrationRequest {
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
    @NotNull
    @NotEmpty
    private String password;
}
