package com.trugroceryassignment.cart_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trugroceryassignment.cart_api.dto.request.AddCartItemRequest;
import com.trugroceryassignment.cart_api.dto.request.UpdateCartItemRequest;
import com.trugroceryassignment.cart_api.dto.response.CartItemResponse;
import com.trugroceryassignment.cart_api.dto.response.CartResponse;
import com.trugroceryassignment.cart_api.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addItem(@Valid @RequestBody AddCartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(request));
    }

    @PatchMapping("/items/{productId}")
    public ResponseEntity<CartItemResponse> updateQuantity(
        @PathVariable Long productId,
        @Valid
        @RequestBody UpdateCartItemRequest request) {
        CartItemResponse response = cartService.updateQuantity(productId, request);
        if(response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }
}
