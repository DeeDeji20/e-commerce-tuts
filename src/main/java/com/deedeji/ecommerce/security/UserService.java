package com.deedeji.ecommerce.security;

import com.deedeji.ecommerce.data.dto.request.LoginRequest;
import com.deedeji.ecommerce.data.dto.response.LoginResponse;
import com.deedeji.ecommerce.data.models.AppUser;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);

    AppUser getUserByUsername(String email);
}
