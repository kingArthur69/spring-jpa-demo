package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerDaoImplTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    CustomerDao customerDao;

    @Test
    void findAllCustomers() {
        assertTrue(customerDao.findAll().size() > 0);
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
        customerDao.deleteByFirstNameAndLastName("TestFirstName", "TestLastName");

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
    void findAllCustomerByLastNameLike() {
        Customer customer = new Customer();
        customer.setLastName("LikeLastName");

        customerDao.save(customer);

        List<Customer> customers = customerDao.findAllCustomerByLastNameLike("LikeLast");

        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    void findCustomersByAddress() {
        Customer customer = new Customer();
        customer.setAddress("TestAddress");
        customerDao.save(customer);

        List<Customer> customers = customerDao.findCustomersByAddress("TestAddress");
        assertTrue(customers.size() > 0);
        assertEquals("TestAddress", customers.get(0).getAddress());
    }

    @Test
    void findAllByFirstName() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customerDao.save(customer);

        List<Customer> customers = customerDao.findAllByFirstName("FirstName");
        assertTrue(customers.size() > 0);
        assertEquals("FirstName", customers.get(0).getFirstName());
    }

    @Test
    void findAllByFirstNameNative() {
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customerDao.save(customer);

        List<Customer> customers = customerDao.findAllByFirstNameNative("FirstName");
        assertTrue(customers.size() > 0);
        assertEquals("FirstName", customers.get(0).getFirstName());
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

        assertNull(customerDao.getById(save.getId()));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        CustomerDao customerDao(EntityManagerFactory entityManagerFactory) {
            return new CustomerDaoImpl(entityManagerFactory);
        }
    }
}