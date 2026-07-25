package com.trugroceryassignment.cart_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trugroceryassignment.cart_api.dto.request.AddCartItemRequest;
import com.trugroceryassignment.cart_api.dto.request.UpdateCartItemRequest;
import com.trugroceryassignment.cart_api.dto.response.BillResponse;
import com.trugroceryassignment.cart_api.dto.response.CartItemResponse;
import com.trugroceryassignment.cart_api.dto.response.CartResponse;
import com.trugroceryassignment.cart_api.entity.CartItem;
import com.trugroceryassignment.cart_api.entity.Product;
import com.trugroceryassignment.cart_api.exception.CartItemNotFoundException;
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

    public CartItemResponse updateQuantity(Long productId, UpdateCartItemRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(productId));
        CartItem cartItem = cartRepository.findByProduct(product)
                .orElseThrow(() ->
                        new CartItemNotFoundException(productId));
        if(request.getQuantity() == 0){
            cartRepository.delete(cartItem);
            return null;
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cartItem);
        return mapToResponse(cartItem);
    }

    public CartResponse getCart() {

        List<CartItem> cartItems = cartRepository.findAll();
        List<CartItemResponse> items = new ArrayList<>();
        int itemTotal = 0;
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            items.add(new CartItemResponse(
                    product.getId(),
                    product.getName(),
                    product.getUnit(),
                    cartItem.getQuantity()
            ));
            itemTotal += product.getPricePaise() * cartItem.getQuantity();
        }
        int deliveryFee = calculateDeliveryFee(itemTotal);
        BillResponse bill = new BillResponse(
            itemTotal,
            deliveryFee,
            itemTotal + deliveryFee
        );
        return new CartResponse(items, bill);
    }

    private int calculateDeliveryFee(int itemTotal) {
        if (itemTotal > 50000) {
            return 0;
        }
        return 3000;
    }
}
