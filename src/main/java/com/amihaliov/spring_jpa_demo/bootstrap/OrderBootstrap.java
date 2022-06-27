package com.amihaliov.spring_jpa_demo.bootstrap;

import com.amihaliov.spring_jpa_demo.model.*;
import com.amihaliov.spring_jpa_demo.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
//@Profile({"default", "contextRefresh" })
public class OrderBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;
    private final PlaceRepository placeRepository;

    public OrderBootstrap(CustomerRepository customerRepository, EmployeeRepository employeeRepository, OrderRepository orderRepository, IngredientRepository ingredientRepository, PlaceRepository placeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
        this.placeRepository = placeRepository;
    }

    private Order getOrder() {
        customerRepository.deleteAll();
        employeeRepository.deleteAll();
        orderRepository.deleteAll();
        ingredientRepository.deleteAll();
        placeRepository.deleteAll();

        Customer customer = new Customer();
        customer.setAddress("Peterson 5, ap 6");
        customer.setFirstName("Pete");
        customer.setPhone("0123456789");

        customerRepository.save(customer);
        log.info("Saved customer " +  customer);

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setPosition("Delivery");
        employee.setSalary(15000d);

        employeeRepository.save(employee);
        log.info("Saved employee " +  employee);

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Potato");

        ingredientRepository.save(ingredient);

        Place place = new Place();
        place.setName("Bob's Pizza");
        place.setAddress("Jefferson st 45 b 9");

        placeRepository.save(place);

        Meal pizza = new Meal();
        pizza.setName("pizza");
        pizza.setPrice(123d);

        Meal burger = new Meal();
        burger.setName("burger");
        burger.setPrice(123d);

        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(LocalDateTime.now());
        order.setMeals(Arrays.asList(pizza, burger));
        order.setEmployee(employee);
        order.setPrice(123d);

        return order;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Order order = getOrder();
        orderRepository.save(order);
        System.out.println("Saved order " +  order);
        log.info("Saved order " +  order);
    }
}
