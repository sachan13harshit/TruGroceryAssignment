package com.trugroceryassignment.cart_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.trugroceryassignment.cart_api.dto.response.ProductResponse;
import com.trugroceryassignment.cart_api.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }
    
}
