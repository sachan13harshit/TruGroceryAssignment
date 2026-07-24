package com.trugroceryassignment.cart_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trugroceryassignment.cart_api.entity.CartItem;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    
}
