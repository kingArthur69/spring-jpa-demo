package com.amihaliov.spring_jpa_demo.repository;

import com.amihaliov.spring_jpa_demo.bootstrap.OrderBootstrap;
import com.amihaliov.spring_jpa_demo.dao.CustomerDao;
import com.amihaliov.spring_jpa_demo.dao.CustomerDaoImpl;
import com.amihaliov.spring_jpa_demo.model.Customer;
import com.amihaliov.spring_jpa_demo.model.Ingredient;
import com.amihaliov.spring_jpa_demo.model.Order;
import com.amihaliov.spring_jpa_demo.model.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"com.amihaliov.spring_jpa_demo.bootstrap", "com.amihaliov.spring_jpa_demo.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegration_Test {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    CustomerDao customerDao;

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

    @Test
    void getCustomerByIdDaoTest() {
        Customer customer = customerDao.getById(1L);

        assertNotNull(customer);
        assertEquals("Pete", customer.getFirstName());
        assertEquals("Jackson", customer.getLastName());
        assertEquals("Peterson 5, ap 6", customer.getAddress());
    }

    @Test
    void getCustomerByFirstNameAndLastNameDaoTest() {
        Customer customer = customerDao.getByFirstNameAndLastName("Pete", "Jackson");

        assertNotNull(customer);
        assertEquals("Pete", customer.getFirstName());
        assertEquals("Jackson", customer.getLastName());
        assertEquals("Peterson 5, ap 6", customer.getAddress());
        assertEquals("0123456789", customer.getPhone());
    }

    @Test
    void saveCustomerTest() {
        Customer customer = new Customer();
        customer.setFirstName("SaveTestName");
        customer.setLastName("SaveLastName");
        customer.setAddress("SaveAddress");
        customer.setPhone("SavePhone");
        Customer save = customerDao.save(customer);

        assertNotNull(save);
        assertEquals("SaveTestName", save.getFirstName());
        assertEquals("SaveLastName", save.getLastName());
        assertEquals("SaveAddress", save.getAddress());
        assertEquals("SavePhone", save.getPhone());
    }

    @Test
    void updateCustomerTest() {
        Customer customer = new Customer();
        customer.setFirstName("NewTestName");
        customer.setLastName("NewLastName");
        customer.setAddress("NewAddress");
        customer.setPhone("NewPhone");
        customer = customerDao.save(customer);


        customer.setFirstName("UpdateTestName");
        customer.setLastName("UpdateLastName");
        customer.setAddress("UpdateAddress");
        customer.setPhone("UpdatePhone");
        Customer update = customerDao.update(customer);

        assertNotNull(update);
        assertEquals("UpdateTestName", update.getFirstName());
        assertEquals("UpdateLastName", update.getLastName());
        assertEquals("UpdateAddress", update.getAddress());
        assertEquals("UpdatePhone", update.getPhone());
    }

    @Test
    void deleteCustomerTest() {
        Customer customer = new Customer();
        customer = customerDao.save(customer);

        customerDao.delete(customer.getId());

        assertNull(customerDao.getById(customer.getId()));
    }


    @TestConfiguration
    static class TestConfig {

        @Bean
        OrderBootstrap orderBootstrap(CustomerRepository customerRepository, EmployeeRepository employeeRepository,
                                      OrderRepository orderRepository, IngredientRepository ingredientRepository,
                                      PlaceRepository placeRepository) {
            return new OrderBootstrap(customerRepository, employeeRepository, orderRepository,
                    ingredientRepository, placeRepository);
        }

        @Bean
        CustomerDao customerDao(DataSource dataSource) {
            return new CustomerDaoImpl(dataSource);
        }
    }
}