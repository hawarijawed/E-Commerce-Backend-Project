package com.ecommerce.ecommerce_backend.dto.Cart;

import lombok.Data;

@Data
public class AddToCart {
    private Long userId;
    private Long productId;
    private int quantity;
}
