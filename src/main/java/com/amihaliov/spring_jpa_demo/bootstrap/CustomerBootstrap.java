package com.amihaliov.spring_jpa_demo.bootstrap;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("commandLine")
public class CustomerBootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public CustomerBootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setFirstName("Tom");
        customer.setLastName("Johnson");
        customer.setAddress("5 abc test street");
        customer.setPhone("45678");

        customerRepository.save(customer);
    }
}
