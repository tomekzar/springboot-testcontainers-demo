package com.cogrammer.testcontainers.controller;

import com.cogrammer.testcontainers.dto.Product;
import com.cogrammer.testcontainers.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@SuppressWarnings("unused")
class ProductController {

    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    List<Product> allProducts(@RequestParam(required = false) String manufacturer) {
        return Optional.ofNullable(manufacturer)
                .map(service::allManufacturerProducts)
                .orElseGet(service::allProducts);
    }
}
