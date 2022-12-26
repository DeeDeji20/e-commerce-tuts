package com.deedeji.ecommerce.data.repository;

import com.deedeji.ecommerce.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
