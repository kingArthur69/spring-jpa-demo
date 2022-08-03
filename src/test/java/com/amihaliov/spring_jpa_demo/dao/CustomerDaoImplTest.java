package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerDaoImplTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void getById() {
        Customer customer = new Customer();
        customer.setFirstName("TestFirstName");
        customer.setLastName("TestLastName");

        customer = customerDao.save(customer);

        assertNotNull(customerDao.getById(customer.getId()));
    }

    @Test
    void getByFirstNameAndLastName() {
        Customer customer = new Customer();
        customer.setFirstName("TestFirstName");
        customer.setLastName("TestLastName");

        customerDao.save(customer);

        customer = customerDao.getByFirstNameAndLastName("TestFirstName", "TestLastName");

        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals("TestFirstName", customer.getFirstName());
        assertEquals("TestLastName", customer.getLastName());
    }

    @Test
    void save() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");

        Customer save = customerDao.save(customer);
        assertNotNull(save);
        assertNotNull(save.getId());
        assertEquals("FirstName", save.getFirstName());
    }

    @Test
    void update() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");

        Customer save = customerDao.save(customer);
        save.setFirstName("NewFirstName");

        Customer update = customerDao.update(save);

        assertEquals("NewFirstName", update.getFirstName());
    }

    @Test
    void delete() {
        Customer customer = new Customer();
        customer.setFirstName("TestDelete");
        Customer save = customerDao.save(customer);

        customerDao.delete(save.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> customerDao.getById(save.getId()));
    }

    @Test
    void findAllPageable() {
        Customer customer = new Customer();
        customer.setFirstName("TestPageable");
        customerDao.save(customer);

        customer = new Customer();
        customer.setFirstName("TestPageable2");
        customerDao.save(customer);

        List<Customer> customers = customerDao.findAll(1, 0);

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("TestPageable", customers.get(0).getFirstName());
    }

    @Test
    void findAllPageable_SecondPage() {
        Customer customer = new Customer();
        customer.setFirstName("TestPageable");
        customerDao.save(customer);

        List<Customer> customers = customerDao.findAll(1, 10);

        assertNotNull(customers);
        assertEquals(0, customers.size());
    }

    @Test
    void findAllSortedByFirstName() {
        Customer customer = new Customer();
        customer.setFirstName("ATestName");

        customerDao.save(customer);

        customer = new Customer();
        customer.setFirstName("BTestName");

        customerDao.save(customer);

        List<Customer> customers = customerDao.findAllSortedByFirstName();

        assertNotNull(customers);
        assertNotNull(customers.get(0));
        assertEquals("ATestName", customers.get(0).getFirstName());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        CustomerDao customerDao(CustomerRepository customerRepository) {
            return new CustomerDaoImpl(customerRepository);
        }
    }
} 