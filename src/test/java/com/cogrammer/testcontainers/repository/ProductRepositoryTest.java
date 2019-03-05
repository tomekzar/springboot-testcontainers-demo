package com.cogrammer.testcontainers.repository;

import com.cogrammer.testcontainers.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = ProductRepositoryTest.Initializer.class)
@Testcontainers
class ProductRepositoryTest {

    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("product-catalog");

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void beforeEach() {
        entityManager.getEntityManager()
                .createQuery("select p from com.cogrammer.testcontainers.model.Product p")
                .getResultList()
                .forEach(entityManager::remove);
        entityManager.persist(Product.of("Apple", "iPhone XS Space Gray 64GB", BigDecimal.valueOf(999)));
        entityManager.persist(Product.of("Apple", "iPhone XS Max Space Gray 64GB", BigDecimal.valueOf(1099)));
        entityManager.persist(Product.of("Samsung", "Samsung Galaxy S9 64GB", BigDecimal.valueOf(719.99)));
        entityManager.flush();
    }

    @Test
    void should_return_all_configured_products() {
        // given

        // when
        final List<Product> products = repository.findAll();

        // then
        assertThat(products).hasSize(3);
    }

    @Test
    void should_return_all_configured_products_for_given_manufacturer() {
        // given

        // when
        final List<Product> products = repository.findAllByManufacturer("Apple");

        // then
        assertThat(products).hasSize(2);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(context);
        }
    }
}
