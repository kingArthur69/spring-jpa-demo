package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order save(Order order);

    Order delete(Order order);


}
