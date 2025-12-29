package com.example.products.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("products")
public class Product {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
}
