package com.cogrammer.testcontainers.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MANUFACTURER", nullable = false)
    private String manufacturer;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    public static Product of(String manufacturer, String name, BigDecimal price) {
        final String currency = Currency.getInstance(Locale.US).getCurrencyCode();
        return new Product(null, manufacturer, name, price, currency);
    }
}
