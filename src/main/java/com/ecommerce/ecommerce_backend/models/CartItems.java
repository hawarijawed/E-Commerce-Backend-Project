package com.ecommerce.ecommerce_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many cart items belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    // Many cart items belong to one cart
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    private Cart cart;

    private int quantity;

    private BigDecimal price = BigDecimal.ZERO;     // Price per unit
    private BigDecimal total = BigDecimal.ZERO;     // Quantity * price
}
