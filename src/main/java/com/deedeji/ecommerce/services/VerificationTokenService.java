package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.VerificationToken;

public interface VerificationTokenService {
    VerificationToken createToken(String email);
    boolean isValidVerificationToken(String token);
}
