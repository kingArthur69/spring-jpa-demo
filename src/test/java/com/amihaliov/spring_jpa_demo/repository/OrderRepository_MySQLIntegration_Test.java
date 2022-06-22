package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepository_MySQLIntegration_Test {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void saveOrderTest() {
        assertEquals(2, orderRepository.count());

        Order order = new Order();
        order.setDate(LocalDateTime.now());

        orderRepository.save(order);

        assertEquals(3, orderRepository.count());
    }
}