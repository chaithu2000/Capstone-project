package com.retail.customer.service;

import org.springframework.stereotype.Service;

import com.retail.customer.entity.Customer;
import com.retail.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
