package com.retail.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
