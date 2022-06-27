package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.model.Ingredient;
import com.amihaliov.spring_jpa_demo.model.Order;
import com.amihaliov.spring_jpa_demo.model.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.amihaliov.spring_jpa_demo.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegration_Test {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Test
    void saveOrderTest() {
        assertEquals(1, orderRepository.count());

        Order order = new Order();
        order.setDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId());

        assertEquals(2, orderRepository.count());
    }

    @Test
    public void saveIngredientTest() {
        Ingredient ingredient = ingredientRepository.save(new Ingredient());

        UUID id = ingredient.getId();

        assertNotNull(id);
        assertNotNull(ingredientRepository.findById(id).get());
    }

    @Test
    void savePlacesTest() {
        Place place = placeRepository.save(new Place());

        UUID id = place.getId();

        assertNotNull(id);
        assertNotNull(placeRepository.findById(id).get());
    }
}