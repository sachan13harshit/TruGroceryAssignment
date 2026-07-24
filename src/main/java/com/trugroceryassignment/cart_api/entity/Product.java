package com.trugroceryassignment.cart_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "price_paise" , nullable = false)
    private Integer pricePaise;

    @Column(nullable = false)
    private String unit;


    public Product(){

    }

    public Product(Long id, String name, Integer pricePaise, String unit) {
        this.id = id;
        this.name = name;
        this.pricePaise = pricePaise;
        this.unit = unit;
    }
}
