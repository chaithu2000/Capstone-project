package com.retail.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.retail.customer.Client.ProductClient;
import com.retail.customer.entity.Purchase;
import com.retail.customer.exception.CustomerNotFoundException;
import com.retail.customer.exception.InsufficientStockException;
import com.retail.customer.exception.NoPurchasesFoundException;
import com.retail.customer.repository.CustomerRepository;
import com.retail.customer.repository.PurchaseRepository;

import feign.FeignException;
import java.util.*;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final ProductClient productClient;

    public Purchase makePurchase(Long customerId, Long productId, Integer quantity) {
        // Check if customer exists
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("No such customer ID present: " + customerId);
        }
        
        Map<String, Object> productMap;
        try {
            productMap = productClient.getProductById(productId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        
        Integer stockQuantity = (Integer) productMap.get("stockQuantity");
        BigDecimal price = new BigDecimal(productMap.get("price").toString()); // Get price from productMap

        if (stockQuantity < quantity) {
            throw new InsufficientStockException("Not enough stock available for product ID: " + productId);
        }

        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        
        productClient.decrementStock(productId, stockQuantity - quantity);

        Purchase purchase = new Purchase(customerId, productId, quantity,totalPrice);
        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getPurchasesByCustomerId(Long customerId) {
        // Check if customer exists
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("No such customer ID present: " + customerId);
        }
        
        List<Purchase> purchases = purchaseRepository.findByCustomerId(customerId);

        if (purchases.isEmpty()) {
            throw new NoPurchasesFoundException("No purchases done yet by customer with ID: " + customerId);
        }

        return purchases;
    }
}
