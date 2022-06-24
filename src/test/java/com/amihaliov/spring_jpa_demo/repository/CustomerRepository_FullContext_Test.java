package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerRepository_FullContext_Test {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomerTest() {
        assertEquals(1, customerRepository.count());

        Customer customer = new Customer();
        customer.setFirstName("FirstNameTest");
        customer.setLastName("LastNameTest");

        customerRepository.save(customer);

        Customer savedCustomer = IterableUtils.toList(customerRepository.findAll())
                .stream()
                .filter(c -> "FirstNameTest".equals(c.getFirstName()) && "LastNameTest".equals(c.getLastName()))
                .findFirst()
                .orElse(null);

        assertNotNull(savedCustomer);

        assertEquals(2, customerRepository.count());
    }
}