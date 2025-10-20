package com.ecommerce.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    private String description;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String brand;
    private int stockQuantity;
    @ElementCollection
    private List<String> category;
}
