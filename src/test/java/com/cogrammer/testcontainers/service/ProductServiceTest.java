package com.cogrammer.testcontainers.service;

import com.cogrammer.testcontainers.config.CacheConfiguration;
import com.cogrammer.testcontainers.model.Product;
import com.cogrammer.testcontainers.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
    ProductService.class,
    CacheConfiguration.class,
    RedisAutoConfiguration.class,
    JacksonAutoConfiguration.class
}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = ProductServiceTest.Initializer.class)
@Testcontainers
class ProductServiceTest {

    @Container
    private static final GenericContainer redisContainer = new GenericContainer("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    @MockBean
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp() {
        when(repository.findAll()).thenReturn(Arrays.asList(
            Product.of("Apple", "iPhone XS Space Gray 64GB", BigDecimal.valueOf(999)),
            Product.of("Apple", "iPhone XS Max Space Gray 64GB", BigDecimal.valueOf(1099)),
            Product.of("Samsung", "Samsung Galaxy S9 64GB", BigDecimal.valueOf(719.99))
        ));
    }

    @Test
    void should_cache_all_products_query_result() {
        // given

        // when
        final List<com.cogrammer.testcontainers.dto.Product> firstResult = service.allProducts();
        final List<com.cogrammer.testcontainers.dto.Product> secondResult = service.allProducts();

        // then
        assertThat(firstResult).isEqualTo(secondResult);
        verify(repository).findAll();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                "spring.redis.host=" + redisContainer.getContainerIpAddress(),
                "spring.redis.port=" + redisContainer.getMappedPort(6379)
            ).applyTo(context);
        }
    }
}
