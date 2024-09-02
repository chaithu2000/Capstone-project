package com.productms.productMicro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private Integer stockQuantity;
    private String category;
    private Double price;

    public Product(String name, Integer stockQuantity, Double price, String category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.category = category;
    }

}
