package com.cogrammer.testcontainers.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

@Getter
@ToString
@Builder
@EqualsAndHashCode
public class Product {

    private final Long id;
    private final String manufacturer;
    private final String name;
    private final BigDecimal price;
    private final Currency currency;

    public static Product of(Long id, String manufacturer, String name, BigDecimal price) {
        return new Product(id, manufacturer, name, price, Currency.getInstance(Locale.US));
    }

    @JsonCreator
    private Product(@JsonProperty(value = "id", required = true) Long id,
                    @JsonProperty(value = "manufacturer", required = true) String manufacturer,
                    @JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "price", required = true) BigDecimal price,
                    @JsonProperty(value = "currency", required = true) Currency currency) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }
}
