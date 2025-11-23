package com.ecommerce.ecommerce_backend.dto.PlaceOrder;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private int priceAtOrderTime;
    private int total;
}
