package com.deedeji.ecommerce.services;

import com.deedeji.ecommerce.data.models.VerificationToken;
import com.deedeji.ecommerce.data.repository.VerificationTokenRepository;
import com.deedeji.ecommerce.util.EcommerceUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService{
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken createToken(String email) {
        var token =VerificationToken.builder()
                .token(EcommerceUtils.generateToken())
                .createdAt(LocalDateTime.now())
                .userEmail(email)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        return verificationTokenRepository.save(token);
    }

    @Override
    public boolean isValidVerificationToken(String token) {
        return false;
    }
}
