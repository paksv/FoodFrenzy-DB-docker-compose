package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Create a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password123");
        user.setUnumber(1234567890L);

        // Save the user
        User savedUser = userRepository.save(user);

        // Verify the user was saved with an ID
        assertThat(savedUser.getU_id()).isPositive();
        assertThat(savedUser.getUname()).isEqualTo("Test User");
        assertThat(savedUser.getUemail()).isEqualTo("test@example.com");
        assertThat(savedUser.getUpassword()).isEqualTo("password123");
        assertThat(savedUser.getUnumber()).isEqualTo(1234567890L);
    }

    @Test
    public void testFindUserById() {
        // Create and persist a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password123");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Find the user by ID
        User foundUser = userRepository.findById(persistedUser.getU_id()).orElse(null);

        // Verify the user was found
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUname()).isEqualTo("Test User");
        assertThat(foundUser.getUemail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindUserByEmail() {
        // Create and persist a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password123");
        user.setUnumber(1234567890L);
        
        entityManager.persistAndFlush(user);

        // Find the user by email
        User foundUser = userRepository.findUserByUemail("test@example.com");

        // Verify the user was found
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUname()).isEqualTo("Test User");
        assertThat(foundUser.getUemail()).isEqualTo("test@example.com");
    }

    @Test
    public void testUpdateUser() {
        // Create and persist a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password123");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Update the user
        persistedUser.setUname("Updated User");
        userRepository.save(persistedUser);

        // Find the user by ID
        User updatedUser = userRepository.findById(persistedUser.getU_id()).orElse(null);

        // Verify the user was updated
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUname()).isEqualTo("Updated User");
        assertThat(updatedUser.getUemail()).isEqualTo("test@example.com");
    }

    @Test
    public void testDeleteUser() {
        // Create and persist a user
        User user = new User();
        user.setUname("Test User");
        user.setUemail("test@example.com");
        user.setUpassword("password123");
        user.setUnumber(1234567890L);
        
        User persistedUser = entityManager.persistAndFlush(user);

        // Delete the user
        userRepository.deleteById(persistedUser.getU_id());

        // Try to find the user by ID
        User deletedUser = userRepository.findById(persistedUser.getU_id()).orElse(null);

        // Verify the user was deleted
        assertThat(deletedUser).isNull();
    }
}