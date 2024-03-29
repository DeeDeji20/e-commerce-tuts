package com.deedeji.ecommerce.data.repository;

import com.deedeji.ecommerce.data.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmail(String email);
}
