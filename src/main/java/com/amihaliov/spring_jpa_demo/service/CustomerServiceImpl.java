package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Set<Customer> findAll() {
        Set<Customer> customers = new HashSet<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer updateCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found by id" + id));

        if(customer.getFirstName() != null) {
            updateCustomer.setFirstName(customer.getFirstName());
        }

        if(customer.getLastName() != null) {
            updateCustomer.setLastName(customer.getLastName());
        }

        if(customer.getPhone() != null) {
            updateCustomer.setPhone(customer.getPhone());
        }

        if(customer.getAddress() != null) {
            updateCustomer.setAddress(customer.getAddress());
        }

        return customerRepository.save(updateCustomer);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
