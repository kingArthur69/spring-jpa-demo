package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Customer getById(Long id) {
        return jdbcTemplate.queryForObject("select * from customers where id = ?", getRowMapper(), id);
    }

    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("select * from customers where first_name = ? and last_name = ?",
                getRowMapper(),
                firstName, lastName);
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update("insert into customers (first_name, last_name) values (?, ?)",
                customer.getFirstName(), customer.getLastName());

        Long id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return this.getById(id);
    }

    @Override
    public Customer update(Customer customer) {
        jdbcTemplate.update("update customers set first_name = ?, last_name = ?",
                customer.getFirstName(), customer.getLastName());

        return this.getById(customer.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from customers where id = ?", id);
    }

    private RowMapper<Customer> getRowMapper() {
        return new CustomerMapper();
    }
}
