package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static final long ID = 1L;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @Captor
    private ArgumentCaptor<Customer> captor;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(ID);
        customer.setFirstName("testFirstName");
        customer.setLastName("testLastName");
        customer.setPhone("0123456789");
        customer.setAddress("testAddress");
    }

    @Test
    void findAll() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        assertEquals(1, customerService.findAll().size());
    }

    @Test
    void create() {
        when(customerRepository.save(any())).thenReturn(customer);


        assertEquals(ID, customerService.create(customer).getId());
    }

    @Test
    void read() {
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        assertEquals(ID, customerService.read(ID).getId());
    }

    @Test
    void update() {
        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        Customer update = new Customer();
        update.setId(ID);
        update.setFirstName("newFirstName");
        update.setLastName("newLastName");
        update.setPhone("newPhone");
        update.setAddress("newAddress");

        customerService.update(ID, update);

        verify(customerRepository).save(captor.capture());

        Customer newCustomer = captor.getValue();

        assertEquals(ID,  newCustomer.getId());
        assertEquals("newFirstName",  newCustomer.getFirstName());
        assertEquals("newLastName",  newCustomer.getLastName());
        assertEquals("newPhone",  newCustomer.getPhone());
        assertEquals("newAddress",  newCustomer.getAddress());
    }

    @Test
    void delete() {
        customerService.delete(ID);

        verify(customerRepository).deleteById(ID);
    }
}