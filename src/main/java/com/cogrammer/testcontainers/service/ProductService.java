package com.cogrammer.testcontainers.service;

import com.cogrammer.testcontainers.dto.Product;
import com.cogrammer.testcontainers.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    private static Product asDto(com.cogrammer.testcontainers.model.Product p) {
        return Product.builder()
                .id(p.getId())
                .manufacturer(p.getManufacturer())
                .name(p.getName())
                .price(p.getPrice())
                .currency(Currency.getInstance(p.getCurrency()))
                .build();
    }

    @Cacheable(value = "products", unless = "#result.isEmpty()", key = "'allProducts'")
    public List<Product> allProducts() {
        return repository.findAll().stream()
                .map(ProductService::asDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", unless = "#result.isEmpty()")
    public List<Product> allManufacturerProducts(String manufacturer) {
        return repository.findAllByManufacturer(manufacturer).stream()
                .map(ProductService::asDto)
                .collect(Collectors.toList());
    }
}
