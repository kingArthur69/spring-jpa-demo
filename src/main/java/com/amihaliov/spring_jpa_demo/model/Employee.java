package com.amihaliov.spring_jpa_demo.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends Person {

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private Double salary;

    @OneToMany(mappedBy = "employee")
    private Set<Order> orders = new HashSet<>();

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
