package com.amihaliov.spring_jpa_demo.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "meals")
public class Meal extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
