package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;

public interface CustomerDao {

    Customer getById(Long id);

    Customer getByFirstNameAndLastName(String firstName, String lastName);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void delete(Long id);
}
