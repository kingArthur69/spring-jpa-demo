package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Use with @Order(1) on test method to set the order of tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
*/
@DataJpaTest
@ComponentScan(basePackages = {"com.amihaliov.spring_jpa_demo.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepository_JpaContext_Test {

    @Autowired
    EmployeeRepository employeeRepository;

    /*
    To turn off automatic rollback of transaction on test finish use either of annotations:
    @Rollback(value = false)
    @Commit
    */
    @Test
    void saveEmployeeTest() {
        assertEquals(1, employeeRepository.count());

        Employee employee = new Employee();
        employee.setFirstName("Tim");
        employee.setLastName("Johnson");

        employeeRepository.save(employee);

        assertEquals(2, employeeRepository.count());
    }
}