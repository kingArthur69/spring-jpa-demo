package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerDaoImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
    }

    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        Customer ref = customerRepository.getReferenceById(customer.getId());
        ref.setFirstName(customer.getFirstName());
        ref.setLastName(customer.getLastName());
        ref.setAddress(customer.getAddress());
        ref.setPhone(customer.getPhone());
        ref.setOrders(customer.getOrders());
        return customerRepository.save(ref);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAll(int pageSize, int offset) {
        PageRequest pageRequest = PageRequest.ofSize(pageSize);

        if (offset > 0) {
            pageRequest = pageRequest.withPage(offset / pageSize);
        } else {
            pageRequest = pageRequest.withPage(0);
        }

        return findAll(pageRequest);
    }

    @Override
    public List<Customer> findAll(Pageable pageable) {
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.getContent();
    }

    @Override
    public List<Customer> findAllSortedByFirstName() {
        return customerRepository.findByOrderByFirstName();
    }
}
