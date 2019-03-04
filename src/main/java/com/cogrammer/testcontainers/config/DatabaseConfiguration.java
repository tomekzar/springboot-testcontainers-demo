package com.cogrammer.testcontainers.config;

import com.cogrammer.testcontainers.model.Product;
import com.cogrammer.testcontainers.repository.ProductRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DatabaseConfiguration {

    @Bean
    @Profile("development")
    public InitializingBean populateDatabase(ProductRepository repository) {
        return () -> {
            final List<Product> products = Arrays.asList(
                    Product.of("Apple", "iPhone XS Space Gray 64GB", BigDecimal.valueOf(999)),
                    Product.of("Apple", "iPhone XS Max Space Gray 64GB", BigDecimal.valueOf(1099)),
                    Product.of("Samsung", "Samsung Galaxy S9+ 64GB", BigDecimal.valueOf(839.99)),
                    Product.of("Samsung", "Samsung Galaxy S9 64GB", BigDecimal.valueOf(719.99))
            );
            repository.saveAll(products);
        };
    }
}
