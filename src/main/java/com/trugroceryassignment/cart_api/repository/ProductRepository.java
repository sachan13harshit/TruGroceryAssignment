package com.trugroceryassignment.cart_api.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trugroceryassignment.cart_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
