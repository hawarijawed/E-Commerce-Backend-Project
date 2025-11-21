package com.ecommerce.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;


    private LocalDateTime orderTime;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatusEnum;

    @OneToMany
    private List<OrderItems> orderItems = new ArrayList<>();

    private String paymentMethod;
    private String shippingAddress;
    private LocalDateTime createdAt = LocalDateTime.now();
}
