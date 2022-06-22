package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Employee;
import com.amihaliov.spring_jpa_demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee read(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found by id" + id));

        if(employee.getFirstName() != null) {
            updateEmployee.setFirstName(employee.getFirstName());
        }

        if(employee.getPosition() != null) {
            updateEmployee.setPosition(employee.getPosition());
        }

        if(employee.getSalary() != null) {
            updateEmployee.setSalary(employee.getSalary());
        }

//        if(employee.getOrders() != null && !employee.getOrders().isEmpty()) {
//            updateEmployee.getOrders().addAll(employee.getOrders());
//        }

        return employeeRepository.save(updateEmployee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
