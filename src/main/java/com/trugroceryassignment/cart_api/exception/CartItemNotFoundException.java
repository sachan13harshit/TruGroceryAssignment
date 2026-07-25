package com.trugroceryassignment.cart_api.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long productId) {
        super("Product with id " + productId + " is not in the cart");
    }
}
