package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerDao {

    Customer getById(Long id);

    Customer getByFirstNameAndLastName(String firstName, String lastName);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void delete(Long id);

    List<Customer> findAll(int pageSize, int offset);

    List<Customer> findAll(Pageable pageable);

    List<Customer> findAllSortedByFirstName();
}
