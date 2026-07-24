package com.trugroceryassignment.cart_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trugroceryassignment.cart_api.dto.response.ProductResponse;
import com.trugroceryassignment.cart_api.entity.Product;
import com.trugroceryassignment.cart_api.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> response = new ArrayList<>();
        for(Product product : products) {
            response.add(mapToResponse(product));
        }
        return response;
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPricePaise(),
                product.getUnit()
        );
    }
}
