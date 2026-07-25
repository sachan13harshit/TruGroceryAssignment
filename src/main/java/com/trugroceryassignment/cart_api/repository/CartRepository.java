package com.trugroceryassignment.cart_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trugroceryassignment.cart_api.entity.CartItem;
import com.trugroceryassignment.cart_api.entity.Product;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByProduct(Product product);
}
