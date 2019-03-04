package com.cogrammer.testcontainers.repository;

import com.cogrammer.testcontainers.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

    List<Product> findAllByManufacturer(String manufacturer);
}
