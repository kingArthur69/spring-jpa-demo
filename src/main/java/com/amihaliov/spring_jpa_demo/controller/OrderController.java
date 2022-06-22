package com.amihaliov.spring_jpa_demo.controller;

import com.amihaliov.spring_jpa_demo.model.Order;
import com.amihaliov.spring_jpa_demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    @ResponseBody
    public Order test() {
       return null;
    }
}
