package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerDaoImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        Customer ref = customerRepository.getReferenceById(customer.getId());
        ref.setFirstName(customer.getFirstName());
        ref.setLastName(customer.getLastName());
        ref.setAddress(customer.getAddress());
        ref.setPhone(customer.getPhone());
        ref.setOrders(customer.getOrders());
        return customerRepository.save(ref);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
