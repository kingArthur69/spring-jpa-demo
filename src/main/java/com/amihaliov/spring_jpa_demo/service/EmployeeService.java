package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Employee;

public interface EmployeeService {
    Employee create(Employee employee);

    Employee read(Long id);

    Employee update(Long id, Employee student);

    void delete(Long id);
}
