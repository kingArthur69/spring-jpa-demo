package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
