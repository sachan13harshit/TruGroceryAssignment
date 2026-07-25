package com.trugroceryassignment.cart_api.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.trugroceryassignment.cart_api.dto.request.AddCartItemRequest;
import com.trugroceryassignment.cart_api.dto.response.CartItemResponse;
import com.trugroceryassignment.cart_api.entity.CartItem;
import com.trugroceryassignment.cart_api.entity.Product;
import com.trugroceryassignment.cart_api.exception.ProductNotFoundException;
import com.trugroceryassignment.cart_api.repository.CartRepository;
import com.trugroceryassignment.cart_api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemResponse addItem(AddCartItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                new ProductNotFoundException(request.getProductId()));

        Optional<CartItem> existing = cartRepository.findByProduct(product);
        CartItem cartItem;

        if(existing.isPresent()) {
            cartItem = existing.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }
        cartRepository.save(cartItem);
        return mapToResponse(cartItem);
    }
    private CartItemResponse mapToResponse(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new CartItemResponse(
            product.getId(),
            product.getName(),
            product.getUnit(),
            cartItem.getQuantity()
        );
    }
}
