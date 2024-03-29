package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.dto.request.LoginRequest;
import com.deedeji.ecommerce.data.dto.response.LoginResponse;
import com.deedeji.ecommerce.data.models.AppUser;
import com.deedeji.ecommerce.exception.UserNotFoundException;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);

    AppUser getUserByUsername(String email) throws UserNotFoundException;
}
