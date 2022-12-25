package com.deedeji.ecommerce.data.repository;

import com.deedeji.ecommerce.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
