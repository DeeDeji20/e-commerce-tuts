package com.deedeji.ecommerce.data.repository;

import com.deedeji.ecommerce.data.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
