package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Create an order
        Orders order = new Orders();
        order.setoName("Test Order");
        order.setoPrice(25.99);
        order.setoQuantity(2);
        order.setOrderDate(new Date());
        order.setTotalAmmout(51.98);
        order.setUser(persistedUser);

        // Save the order
        Orders savedOrder = orderRepository.save(order);

        // Verify the order was saved with an ID
        assertThat(savedOrder.getoId()).isPositive();
        assertThat(savedOrder.getoName()).isEqualTo("Test Order");
        assertThat(savedOrder.getoPrice()).isEqualTo(25.99);
        assertThat(savedOrder.getoQuantity()).isEqualTo(2);
        assertThat(savedOrder.getTotalAmmout()).isEqualTo(51.98);
        assertThat(savedOrder.getUser()).isEqualTo(persistedUser);
    }

    @Test
    public void testFindOrderById() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Create and persist an order
        Orders order = new Orders();
        order.setoName("Test Order");
        order.setoPrice(25.99);
        order.setoQuantity(2);
        order.setOrderDate(new Date());
        order.setTotalAmmout(51.98);
        order.setUser(persistedUser);
        
        Orders persistedOrder = entityManager.persistAndFlush(order);

        // Find the order by ID
        Orders foundOrder = orderRepository.findById(persistedOrder.getoId()).orElse(null);

        // Verify the order was found
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getoName()).isEqualTo("Test Order");
        assertThat(foundOrder.getoPrice()).isEqualTo(25.99);
        assertThat(foundOrder.getUser().getU_id()).isEqualTo(persistedUser.getU_id());
    }

    @Test
    public void testFindOrdersByUser() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Create and persist multiple orders for the user
        Orders order1 = new Orders();
        order1.setoName("Test Order 1");
        order1.setoPrice(25.99);
        order1.setoQuantity(2);
        order1.setOrderDate(new Date());
        order1.setTotalAmmout(51.98);
        order1.setUser(persistedUser);
        
        Orders order2 = new Orders();
        order2.setoName("Test Order 2");
        order2.setoPrice(15.99);
        order2.setoQuantity(1);
        order2.setOrderDate(new Date());
        order2.setTotalAmmout(15.99);
        order2.setUser(persistedUser);
        
        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        // Find orders by user
        List<Orders> userOrders = orderRepository.findOrdersByUser(persistedUser);

        // Verify the orders were found
        assertThat(userOrders).isNotNull();
        assertThat(userOrders.size()).isEqualTo(2);
        assertThat(userOrders).extracting(Orders::getoName)
                             .containsExactlyInAnyOrder("Test Order 1", "Test Order 2");
    }

    @Test
    public void testUpdateOrder() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Create and persist an order
        Orders order = new Orders();
        order.setoName("Test Order");
        order.setoPrice(25.99);
        order.setoQuantity(2);
        order.setOrderDate(new Date());
        order.setTotalAmmout(51.98);
        order.setUser(persistedUser);
        
        Orders persistedOrder = entityManager.persistAndFlush(order);

        // Update the order
        persistedOrder.setoName("Updated Order");
        persistedOrder.setoQuantity(3);
        persistedOrder.setTotalAmmout(77.97);
        orderRepository.save(persistedOrder);

        // Find the order by ID
        Orders updatedOrder = orderRepository.findById(persistedOrder.getoId()).orElse(null);

        // Verify the order was updated
        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getoName()).isEqualTo("Updated Order");
        assertThat(updatedOrder.getoQuantity()).isEqualTo(3);
        assertThat(updatedOrder.getTotalAmmout()).isEqualTo(77.97);
    }

    @Test
    public void testDeleteOrder() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Create and persist an order
        Orders order = new Orders();
        order.setoName("Test Order");
        order.setoPrice(25.99);
        order.setoQuantity(2);
        order.setOrderDate(new Date());
        order.setTotalAmmout(51.98);
        order.setUser(persistedUser);
        
        Orders persistedOrder = entityManager.persistAndFlush(order);

        // Delete the order
        orderRepository.deleteById(persistedOrder.getoId());

        // Try to find the order by ID
        Orders deletedOrder = orderRepository.findById(persistedOrder.getoId()).orElse(null);

        // Verify the order was deleted
        assertThat(deletedOrder).isNull();
    }
}