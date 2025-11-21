package com.ecommerce.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
    private int quantity;
    private BigDecimal priceAtOrderTime;
    private BigDecimal total;
}
