package com.deedeji.ecommerce.data.repository;

import com.deedeji.ecommerce.data.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
