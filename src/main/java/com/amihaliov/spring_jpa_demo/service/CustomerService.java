package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Customer;

import java.util.Set;

public interface CustomerService {

    Set<Customer> findAll();

    Customer create(Customer customer);

    Customer read(Long id);

    Customer update(Long id, Customer student);

    void delete(Long id);
}
