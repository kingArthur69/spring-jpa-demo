package com.amihaliov.spring_jpa_demo.controller;

import com.amihaliov.spring_jpa_demo.model.Employee;
import com.amihaliov.spring_jpa_demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {

        Employee  createdEmployee = employeeService.create(employee);
        if (createdEmployee == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdEmployee.getId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdEmployee);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> read(@PathVariable("id") Long id) {
        Employee foundEmployee = employeeService.read(id);
        if (foundEmployee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundEmployee);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@RequestBody Employee student, @PathVariable Long id) {
        Employee updatedEmployee = employeeService.update(id, student);
        if (updatedEmployee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedEmployee);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
