package com.amihaliov.spring_jpa_demo.service;

import com.amihaliov.spring_jpa_demo.model.Order;
import com.amihaliov.spring_jpa_demo.repository.OrderRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findAll() {
        return IterableUtils.toList(repository.findAll());
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Order delete(Order order) {
        return null;
    }
}
