package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.Admin;

@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testSaveAdmin() {
        // Create an admin
        Admin admin = new Admin();
        admin.setAdminName("Test Admin");
        admin.setAdminEmail("admin@example.com");
        admin.setAdminPassword("adminpass");
        admin.setAdminNumber("9876543210");

        // Save the admin
        Admin savedAdmin = adminRepository.save(admin);

        // Verify the admin was saved with an ID
        assertThat(savedAdmin.getAdminId()).isPositive();
        assertThat(savedAdmin.getAdminName()).isEqualTo("Test Admin");
        assertThat(savedAdmin.getAdminEmail()).isEqualTo("admin@example.com");
        assertThat(savedAdmin.getAdminPassword()).isEqualTo("adminpass");
        assertThat(savedAdmin.getAdminNumber()).isEqualTo("9876543210");
    }

    @Test
    public void testFindAdminById() {
        // Create and persist an admin
        Admin admin = new Admin();
        admin.setAdminName("Test Admin");
        admin.setAdminEmail("admin@example.com");
        admin.setAdminPassword("adminpass");
        admin.setAdminNumber("9876543210");
        
        Admin persistedAdmin = entityManager.persistAndFlush(admin);

        // Find the admin by ID
        Admin foundAdmin = adminRepository.findById(persistedAdmin.getAdminId()).orElse(null);

        // Verify the admin was found
        assertThat(foundAdmin).isNotNull();
        assertThat(foundAdmin.getAdminName()).isEqualTo("Test Admin");
        assertThat(foundAdmin.getAdminEmail()).isEqualTo("admin@example.com");
    }

    @Test
    public void testFindAdminByEmail() {
        // Create and persist an admin
        Admin admin = new Admin();
        admin.setAdminName("Test Admin");
        admin.setAdminEmail("admin@example.com");
        admin.setAdminPassword("adminpass");
        admin.setAdminNumber("9876543210");
        
        entityManager.persistAndFlush(admin);

        // Find the admin by email
        Admin foundAdmin = adminRepository.findByAdminEmail("admin@example.com");

        // Verify the admin was found
        assertThat(foundAdmin).isNotNull();
        assertThat(foundAdmin.getAdminName()).isEqualTo("Test Admin");
        assertThat(foundAdmin.getAdminEmail()).isEqualTo("admin@example.com");
    }

    @Test
    public void testUpdateAdmin() {
        // Create and persist an admin
        Admin admin = new Admin();
        admin.setAdminName("Test Admin");
        admin.setAdminEmail("admin@example.com");
        admin.setAdminPassword("adminpass");
        admin.setAdminNumber("9876543210");
        
        Admin persistedAdmin = entityManager.persistAndFlush(admin);

        // Update the admin
        persistedAdmin.setAdminName("Updated Admin");
        persistedAdmin.setAdminNumber("5555555555");
        adminRepository.save(persistedAdmin);

        // Find the admin by ID
        Admin updatedAdmin = adminRepository.findById(persistedAdmin.getAdminId()).orElse(null);

        // Verify the admin was updated
        assertThat(updatedAdmin).isNotNull();
        assertThat(updatedAdmin.getAdminName()).isEqualTo("Updated Admin");
        assertThat(updatedAdmin.getAdminNumber()).isEqualTo("5555555555");
        assertThat(updatedAdmin.getAdminEmail()).isEqualTo("admin@example.com");
    }

    @Test
    public void testDeleteAdmin() {
        // Create and persist an admin
        Admin admin = new Admin();
        admin.setAdminName("Test Admin");
        admin.setAdminEmail("admin@example.com");
        admin.setAdminPassword("adminpass");
        admin.setAdminNumber("9876543210");
        
        Admin persistedAdmin = entityManager.persistAndFlush(admin);

        // Delete the admin
        adminRepository.deleteById(persistedAdmin.getAdminId());

        // Try to find the admin by ID
        Admin deletedAdmin = adminRepository.findById(persistedAdmin.getAdminId()).orElse(null);

        // Verify the admin was deleted
        assertThat(deletedAdmin).isNull();
    }
}