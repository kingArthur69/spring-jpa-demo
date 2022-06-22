package com.amihaliov.spring_jpa_demo.controller;

import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {

        Customer  createdCustomer = customerService.create(customer);
        if (createdCustomer == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdCustomer.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdCustomer);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> read(@PathVariable("id") Long id) {
        Customer foundCustomer = customerService.read(id);
        if (foundCustomer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundCustomer);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@RequestBody Customer student, @PathVariable Long id) {
        Customer updatedCustomer = customerService.update(id, student);
        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedCustomer);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
