package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Use with @Order(1) on test method to set the order of tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
*/
@DataJpaTest
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
        assertEquals(0, employeeRepository.count());

        Employee employee = new Employee();
        employee.setFirstName("Tim");
        employee.setLastName("Johnson");

        employeeRepository.save(employee);

        assertEquals(1, employeeRepository.count());
    }
}