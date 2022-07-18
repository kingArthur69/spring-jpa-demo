package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepository_DataJpa_Test {

    @Autowired
    CustomerRepository customerRepository;
    private Customer save;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");
        customer.setAddress("Address");
        customer.setPhone("01234566879");

        save = customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.delete(save);
    }

    @Test
    void findByAddressNative() {
        List<Customer> customers = customerRepository.findByAddressNative("Address");

        assertTrue(customers.size() > 0);
        assertEquals("Address", customers.get(0).getAddress());
    }

    @Test
    void findByLastNameQuery() {
        List<Customer> customers = customerRepository.findByLastNameQuery("LastName");

        assertTrue(customers.size() > 0);
        assertEquals("LastName", customers.get(0).getLastName());
    }

    @Test
    void findByFisrtNameQuery() {
        List<Customer> customers = customerRepository.findByFisrtNameQuery("FirstName");

        assertTrue(customers.size() > 0);
        assertEquals("FirstName", customers.get(0).getFirstName());
    }

    @Test
    void jpaNamed() {
        List<Customer> customers = customerRepository.jpaNamed("01234566879");

        assertTrue(customers.size() > 0);
        assertEquals("01234566879", customers.get(0).getPhone());
    }
}