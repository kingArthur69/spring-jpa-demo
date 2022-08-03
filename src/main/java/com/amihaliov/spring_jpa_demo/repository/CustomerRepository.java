package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select * from customers where address = :address", nativeQuery = true)
    List<Customer> findByAddressNative(@Param("address") String address);

    @Query("select c from Customer c where c.lastName = :lastName")
    List<Customer> findByLastNameQuery(@Param("lastName") String lastName);

    @Query("select c from Customer c where c.firstName = ?1")
    List<Customer> findByFisrtNameQuery(String firstName);

    List<Customer> jpaNamed(@Param("phone") String phone);

    Customer findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByOrderByFirstName();
}
