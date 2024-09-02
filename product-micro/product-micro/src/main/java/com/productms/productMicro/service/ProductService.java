package com.productms.productMicro.service;	

import lombok.RequiredArgsConstructor;

import org.springframework.util.StringUtils;

import com.productms.productMicro.entity.Product;
import com.productms.productMicro.exception.ProductNotFoundException;
import com.productms.productMicro.repository.ProductRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    
    // Search products by name
    public List<Product> getProductsByName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Product name must not be empty");
        }

        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with name: " + name);
        }
        return products;
    }
    
    // Search products by category
    public List<Product> getProductsByCategory(String category) {
        if (!StringUtils.hasText(category)) {
            throw new IllegalArgumentException("Product category must not be empty");
        }

        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found in category: " + category);
        }
        return products;
    }

    public void incrementStock(Long id, int increment) {
        Product product = getProductById(id);
        int currentStock = product.getStockQuantity();
        product.setStockQuantity(currentStock + increment);
        productRepository.save(product);

    }
    
    public void decrementStock(Long id, int decrement) {
        Product product = getProductById(id);
        int currentStock = product.getStockQuantity();
        
        if (currentStock < decrement) {
            throw new RuntimeException("Insufficient stock for product: " + id);
        }
        
        product.setStockQuantity(currentStock - decrement);
        productRepository.save(product);
    }
    
    public Product addProduct(String name, Integer stockQuantity, Double price, String category) {
        Product product = new Product(name, stockQuantity, price, category);
        return productRepository.save(product);
    }
}
