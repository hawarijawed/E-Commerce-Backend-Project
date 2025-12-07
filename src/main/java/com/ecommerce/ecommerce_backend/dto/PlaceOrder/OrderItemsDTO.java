package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderItemsDTO {
    private Long productId;
    private String product;
    private int quantity;
    private BigDecimal priceAtOrderTime;
    private BigDecimal total;
}
