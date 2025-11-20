package com.ecommerce.ecommerce_backend.dto.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartDTO {
    @NotNull
    private Long productId;
    @NotNull
    private int quantity;
}
