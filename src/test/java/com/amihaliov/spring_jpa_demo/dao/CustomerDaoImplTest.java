package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerDaoImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerDao customerDao;

    private Long id;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("insert into customers (first_name, last_name) values ('TestFirstName', 'TestLastName')");
        id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
    }

    @Test
    void getById() {
        assertNotNull(customerDao.getById(id));
    }

    @Test
    void getByFirstNameAndLastName() {
        Customer customer = customerDao.getByFirstNameAndLastName("TestFirstName", "TestLastName");

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

        assertThrows(EmptyResultDataAccessException.class, () -> customerDao.getById(save.getId()));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        CustomerDao customerDao(JdbcTemplate jdbcTemplate) {
            return new CustomerDaoImpl(jdbcTemplate);
        }
    }
}