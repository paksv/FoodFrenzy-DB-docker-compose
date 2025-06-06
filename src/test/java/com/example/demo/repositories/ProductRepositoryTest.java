package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        // Create a product
        Product product = new Product();
        product.setPname("Test Product");
        product.setPprice(99.99);
        product.setPdescription("This is a test product");

        // Save the product
        Product savedProduct = productRepository.save(product);

        // Verify the product was saved with an ID
        assertThat(savedProduct.getPid()).isPositive();
        assertThat(savedProduct.getPname()).isEqualTo("Test Product");
        assertThat(savedProduct.getPprice()).isEqualTo(99.99);
        assertThat(savedProduct.getPdescription()).isEqualTo("This is a test product");
    }

    @Test
    public void testFindProductById() {
        // Create and persist a product
        Product product = new Product();
        product.setPname("Test Product");
        product.setPprice(99.99);
        product.setPdescription("This is a test product");
        
        Product persistedProduct = entityManager.persistAndFlush(product);

        // Find the product by ID
        Product foundProduct = productRepository.findById(persistedProduct.getPid()).orElse(null);

        // Verify the product was found
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getPname()).isEqualTo("Test Product");
        assertThat(foundProduct.getPprice()).isEqualTo(99.99);
        assertThat(foundProduct.getPdescription()).isEqualTo("This is a test product");
    }

    @Test
    public void testFindProductByName() {
        // Create and persist a product
        Product product = new Product();
        product.setPname("Test Product");
        product.setPprice(99.99);
        product.setPdescription("This is a test product");
        
        entityManager.persistAndFlush(product);

        // Find the product by name
        Product foundProduct = productRepository.findByPname("Test Product");

        // Verify the product was found
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getPname()).isEqualTo("Test Product");
        assertThat(foundProduct.getPprice()).isEqualTo(99.99);
        assertThat(foundProduct.getPdescription()).isEqualTo("This is a test product");
    }

    @Test
    public void testUpdateProduct() {
        // Create and persist a product
        Product product = new Product();
        product.setPname("Test Product");
        product.setPprice(99.99);
        product.setPdescription("This is a test product");
        
        Product persistedProduct = entityManager.persistAndFlush(product);

        // Update the product
        persistedProduct.setPname("Updated Product");
        persistedProduct.setPprice(149.99);
        productRepository.save(persistedProduct);

        // Find the product by ID
        Product updatedProduct = productRepository.findById(persistedProduct.getPid()).orElse(null);

        // Verify the product was updated
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getPname()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPprice()).isEqualTo(149.99);
        assertThat(updatedProduct.getPdescription()).isEqualTo("This is a test product");
    }

    @Test
    public void testDeleteProduct() {
        // Create and persist a product
        Product product = new Product();
        product.setPname("Test Product");
        product.setPprice(99.99);
        product.setPdescription("This is a test product");
        
        Product persistedProduct = entityManager.persistAndFlush(product);

        // Delete the product
        productRepository.deleteById(persistedProduct.getPid());

        // Try to find the product by ID
        Product deletedProduct = productRepository.findById(persistedProduct.getPid()).orElse(null);

        // Verify the product was deleted
        assertThat(deletedProduct).isNull();
    }
}