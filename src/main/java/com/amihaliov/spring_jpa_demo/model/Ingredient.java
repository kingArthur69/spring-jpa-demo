package com.amihaliov.spring_jpa_demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
