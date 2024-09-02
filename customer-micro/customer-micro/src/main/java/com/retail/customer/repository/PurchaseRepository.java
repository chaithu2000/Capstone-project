package com.retail.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.customer.entity.Purchase;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerId(Long customerId);
}
