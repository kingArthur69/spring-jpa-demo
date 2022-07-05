package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> findAll();

    Customer getById(Long id);

    Customer getByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findAllCustomerByLastNameLike(String lastName);

    List<Customer> findCustomersByAddress(String address);

    List<Customer> findAllByFirstName(String firstName);

    List<Customer> findAllByFirstNameNative(String firstName);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void delete(Long id);

    void deleteByFirstNameAndLastName(String firstName, String lastName);
}
